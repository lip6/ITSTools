package smtanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.serialization.SerializationUtil;

public class DDrunner {

	private static final int DEBUG = 1;
	private MccTranslator reader;
	private String workFolder;
	private long timeout;
	private CommandLine cl;
	private String smtFile;

	public DDrunner(MccTranslator reader, String workFolder, long timeout) {
		this.reader = reader;
		this.workFolder = workFolder;
		this.timeout = timeout;
	}

	public String outputGalFile() throws IOException {
		File file = Files.createTempFile("ddtosmt", ".gal").toFile();
		if (DEBUG == 0)
			file.deleteOnExit();
		String outpath = file.getCanonicalPath();
		SerializationUtil.systemToFile(reader.getSpec(), outpath, false);
		return outpath;
	}

	public String getSmtFile() {
		return smtFile;
	}
	
	public void run() throws IOException {
		buildCommandLine();

		ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
		pb.directory(cl.getWorkingDir());
		pb.redirectErrorStream(true);

		System.out.println("Invoking ITS tools like this :" + cl);
		try {
			final Process process = pb.start();

			Thread runnerThread = new Thread(() -> {
				try {
					int status = Runner.waitForOrTimeout(timeout, TimeUnit.SECONDS, cl, process);
					if (status != 0) {
						System.err.println("ITS-tools command line returned an error code " + status);
					} else {
						try (BufferedReader in = process.inputReader()) {
							while (in.ready()) {
								String line = in.readLine();
								System.out.println(line);
							}
						} catch (IOException e) {
							System.out.println("Error reading output of ITS tools." + e);
						}
					}
				} catch (InterruptedException e) {
					System.out.println("ITS tools runne²r thread asked to quit. Dying gracefully.");
					return;
				} catch (TimeoutException e) {
					System.out.println("Detected timeout of ITS tools.");
					return;
				}
			});
			runnerThread.start();

		} catch (IOException e) {
			System.out.println("Failure when invoking ITS tools." + e);
			return;
		}
	}

	public void buildCommandLine() throws IOException {
		CommandLineBuilder clb = new CommandLineBuilder(Tool.reach);
		reader.createSPN(true, true);
		reader.rebuildSpecification(new ConcurrentHashDoneProperties());
		String modelff = outputGalFile();
		clb.setModelFile(modelff);
		clb.setModelType("CGAL");
		cl = clb.getCommandLine();
		cl.addArg("-exportsmt");
		smtFile = new File(workFolder, "model.smt2").getCanonicalPath();
		cl.addArg(smtFile);
		cl.setWorkingDir(new File(workFolder));
	}

}
