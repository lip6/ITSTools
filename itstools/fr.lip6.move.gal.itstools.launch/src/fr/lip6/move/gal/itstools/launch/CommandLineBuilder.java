package fr.lip6.move.gal.itstools.launch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.FusionBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;
import fr.lip6.move.gal.itstools.preference.PreferenceConstants;
import fr.lip6.move.gal.louvain.GraphBuilder;
import fr.lip6.move.gal.options.ui.CommonLaunchConstants;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;
import fr.lip6.move.serialization.BasicGalSerializer;
import fr.lip6.move.serialization.SerializationUtil;

public class CommandLineBuilder {

	private static final String PLUGIN_ID = "fr.lip6.move.gal.itstools.launch";

	/** Build a command line from a LaunchConfiguration 
	 * @throws CoreException */
	public static CommandLine buildCommand(ILaunchConfiguration configuration) throws CoreException {
		CommandLine cl = new CommandLine();

		// Path to source model file
		String oriString = configuration.getAttribute(CommonLaunchConstants.MODEL_FILE, "model.gal");		

		// Produce a GAL file to give to its-tools
		IPath oriPath = Path.fromPortableString(oriString);

		// work folder
		File workingDirectory ;
		workingDirectory = new File (oriPath.removeLastSegments(1).append("/work/").toString());

		try {
			workingDirectory.mkdir();
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create work folder :"+workingDirectory+". Please check location is open to write in.",e));
		}
		
		// parse it
		Specification spec = SerializationUtil.fileToGalSystem(oriString);

		// flatten it
		GALRewriter.flatten(spec, true);
		CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
		
		// Do we need to decompose it		
		if (configuration.getAttribute(LaunchConstants.ORDER_FLAGS, new ArrayList<>()).contains("-louvain")) {
			
			if (spec.getMain() instanceof CompositeTypeDeclaration) {
				FusionBuilder.toSingleGAL(spec);
				try {
					SerializationUtil.systemToFile(spec, workingDirectory.getPath() + "/" + oriPath.lastSegment()+".fuse.gal");
				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create working file :"+workingDirectory.getPath() + "/" + oriPath.lastSegment()+".fuse.gal"+". Please check location is open to write in.",e));
				}
			}
			
			INextBuilder inb = INextBuilder.build(spec);
			boolean hasLarge = false;
			for ( Integer init: inb.getInitial()) {
				if (init >= 10) {
					// avoid hierarchy
					hasLarge=true;
					break;
				}
			}

			if (! hasLarge) {
				try {
					List<BitSet> constraints = new ArrayList<>();
					for (Property p : spec.getProperties()) {
						for (TreeIterator<EObject> it=p.eAllContents() ; it.hasNext() ;) {
							EObject obj = it.next();
							if (obj instanceof Comparison) {
								Comparison cmp = (Comparison) obj;
								BitSet tmp = new BitSet();
								NextSupportAnalyzer.computeQualifiedSupport(cmp, tmp, inb);
								if (tmp.cardinality() > 1) {
									constraints.add(tmp);
								}
							}
						}
					}
					IOrder order = GraphBuilder.computeLouvain(inb,true,constraints);
					
					CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getMain(), order);
				} catch (Exception e) {
					log.warning("Could not build decomposition of model due to "+e);
					e.printStackTrace();
				}
				// getLog().fine(order.toString());
			}
		}

		String tmpPath = workingDirectory.getPath() + "/" +oriPath.lastSegment();		
		File modelff = new File(tmpPath);

		List<Property> props = new ArrayList<Property>(spec.getProperties());
		spec.getProperties().clear();
		try {
			SerializationUtil.systemToFile(spec, tmpPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create working file :"+tmpPath+". Please check location is open to write in.",e));
		}
		
		// Path to ITS-reach exe				
		String itsExePath;
		String tool = configuration.getAttribute(CommonLaunchConstants.TOOL, "its-reach");
		if ("its-reach".equals(tool)) {
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE));
		} else if ("its-ctl".equals(tool)) { 
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSCTL_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSCTL_EXE));
		} else {
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSLTL_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSLTL_EXE));
		}
		cl.addArg(itsExePath);

		cl.setWorkingDir(workingDirectory);

		// Input file options
		cl.addArg("-i") ;
		cl.addArg(modelff.getName());

		// Model type option
		cl.addArg("-t");
		if (spec.getMain() != null)
			cl.addArg("CGAL");
		else 
			cl.addArg("GAL");

		// add interpretation of options.		
		for (String flag : configuration.getAttribute(LaunchConstants.COMMON_FLAGS, new ArrayList<>())) {
			cl.addArg(flag);
		}		

		
		if ("its-reach".equals(tool)) {
			List<Property> safeProps = new ArrayList<Property>();
			BasicGalSerializer bgs = new BasicGalSerializer(true);
			for (Property prop : props) {				
				if (prop.getBody() instanceof BoundsProp) {
					BoundsProp bp = (BoundsProp) prop.getBody();
					Property target = prop;
					int toadd=0;
					for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
						EObject obj = it.next();
						if (obj instanceof Constant) {
							Constant cte = (Constant) obj;
							toadd += cte.getValue();
						} else if (obj instanceof Reference) {
							it.prune();
						}
					}
					if (toadd != 0) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bgs.serialize(bp.getTarget(), bos);
						String targetVar = bos.toString();

						Logger.getLogger("fr.lip6.move.gal").warning("For property "+target.getName() + " will report bounds of "+targetVar+ " without constants. Add "+toadd+" to the result in the trace.");;
					}
					safeProps.add(prop);
				} else if (prop.getBody() instanceof SafetyProp) {
					safeProps.add(prop);
				}
			}
			if (! safeProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".prop";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSTools(modelff.getName(), safeProps, propPath);

					// property file arguments
					cl.addArg("-reachable-file");
					cl.addArg(new File(propPath).getName());

				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}
			for (String flag : configuration.getAttribute(LaunchConstants.REACH_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		} else if ("its-ctl".equals(tool)) { 
			List<Property> ctlProps = new ArrayList<Property>(); 
			for (Property prop : props) {
				if (prop.getBody() instanceof CTLProp) {
					ctlProps.add(prop);
				} 
			}
			if (! ctlProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".ctl";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSCTLTools(modelff.getName(), ctlProps, propPath);

					// property file arguments
					cl.addArg("-ctl");
					cl.addArg(new File(propPath).getName());					
				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}
			for (String flag : configuration.getAttribute(LaunchConstants.CTL_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}

		} else {
			List<Property> ltlProps = new ArrayList<Property>(); 
			for (Property prop : props) {
				if (prop.getBody() instanceof LTLProp) {
					ltlProps.add(prop);
				} 
			}
			if (! ltlProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".ltl";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSLTLTools(modelff.getName(), ltlProps, propPath);

					// property file arguments
					cl.addArg("-LTL");
					cl.addArg(new File(propPath).getName());
					cl.addArg("-c");
				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}	
			for (String flag : configuration.getAttribute(LaunchConstants.LTL_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}

		}


		//System.out.println("\n"+cl);
		return cl;
	}
	
	private static Logger log = Logger.getLogger("fr.lip6.move.gal");
}
