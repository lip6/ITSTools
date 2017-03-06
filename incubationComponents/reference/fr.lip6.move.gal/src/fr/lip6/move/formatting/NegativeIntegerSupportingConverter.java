package fr.lip6.move.formatting;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

public class NegativeIntegerSupportingConverter extends
		DefaultTerminalConverters {

	

	@ValueConverter(rule = "Integer")
	public IValueConverter<Integer> Integer() {
		return new IValueConverter<Integer>() {
			@Override
			public Integer toValue(String string, INode node)
					throws ValueConverterException {
				try {
					return Integer.parseInt(string);
				} catch (NumberFormatException nfe) {
					throw new ValueConverterException("String " +string +" is not a number", node, nfe);
				}
			}
			@Override
			public String toString(Integer value) throws ValueConverterException {
				return Integer.toString(value);
			}
		};
	}
}
