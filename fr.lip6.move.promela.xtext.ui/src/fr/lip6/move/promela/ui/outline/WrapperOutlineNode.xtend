package fr.lip6.move.promela.ui.outline

import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.AbstractOutlineNode

//inspired from: http://stackoverflow.com/questions/6929456/group-outline-nodes
class WrapperOutlineNode extends AbstractOutlineNode {
	new(IOutlineNode parent, String text) {
		this(parent, text, null)
	}

	new(IOutlineNode parent, String text, String image) {

		// image from text
		super(
			parent,
			ImageDescriptor.createFromFile(null, "icons/" + image),
			text,
			false
		)

	//        var b = new PluginImageHelper()     
	//        var Image a = b.getImageDescription(image)
	//TODO
	//Image ou ImageDescriptor
	//  this.setImage(a)
	}
}
