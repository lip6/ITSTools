package fr.lip6.move.gal.cegar.checkers;

import fr.lip6.move.gal.cegar.interfaces.IResult;

public class CheckResult implements IResult{
	
	private boolean checked;
	private String[] result;
	private boolean isPropertyTrue;
	
	public CheckResult(boolean isPropertyTrue, boolean checked, String[] result) {
		this.isPropertyTrue = isPropertyTrue;
		this.checked = checked;
		this.result = result;
	}

	@Override
	public boolean hasTrace() {
		return this.checked;
	}

	@Override
	public String[] getTrace() {
		return this.result;
	}

	@Override
	public boolean isPropertyTrue() {
		return isPropertyTrue;
	}

}
