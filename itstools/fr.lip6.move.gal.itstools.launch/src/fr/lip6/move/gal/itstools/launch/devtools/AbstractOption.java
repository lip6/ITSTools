package fr.lip6.move.gal.itstools.launch.devtools;

public abstract class AbstractOption<T> implements IOption<T> {

	private String name;
	private String toolTip;
	private T defaultValue;

	public AbstractOption(String name, String toolTip, T defaultValue) {
		this.name = name;
		this.toolTip = toolTip;
		this.defaultValue = defaultValue;
	}

	@Override
	public final T getDefaultValue() {
		return defaultValue;
	}

	@Override
	public final String getToolTip() {
		return toolTip;
	}

	@Override
	public final String getName() {
		return name;
	}
}
