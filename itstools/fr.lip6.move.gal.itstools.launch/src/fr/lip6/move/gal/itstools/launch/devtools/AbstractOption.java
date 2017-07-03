package fr.lip6.move.gal.itstools.launch.devtools;


/**
 * Abstract class carrying common elements to all IOption<T> implementations.
 */
public abstract class AbstractOption<T> implements IOption<T> {

	private final String name;
	private final String toolTip;
	private final T defaultValue;

	public AbstractOption(String name, String toolTip, T defaultValue) {
		this.name = name;
		this.toolTip = toolTip;
		this.defaultValue = defaultValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final T getDefaultValue() {
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
