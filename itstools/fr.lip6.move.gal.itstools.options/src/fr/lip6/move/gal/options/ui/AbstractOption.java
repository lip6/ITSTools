package fr.lip6.move.gal.options.ui;


/**
 * Abstract class carrying common elements to all IOption implementations.
 */
public abstract class AbstractOption<T> implements IOption {

	private final String name;
	private final String toolTip;
	private final T defaultValue;

	public AbstractOption(String name, String toolTip, T defaultValue) {
		this.name = name;
		this.toolTip = toolTip;
		this.defaultValue = defaultValue;
	}

	
	/**
	 * Options have a default value, used to initialize new configurations.
	 * @return the default value
	 */
	public T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getToolTip() {
		return toolTip;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getName() {
		return name;
	}
}
