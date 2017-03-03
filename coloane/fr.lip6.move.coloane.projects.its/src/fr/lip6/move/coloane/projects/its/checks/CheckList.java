/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.checks;

import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;
import fr.lip6.move.coloane.projects.its.order.Orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CheckList extends SimpleObservable implements
		Iterable<AbstractCheckService>, ISimpleObserver {

	private ITypeDeclaration type;
	private List<AbstractCheckService> services;

	public CheckList(ITypeDeclaration td) {
		type = td;
		services = new ArrayList<AbstractCheckService>();
		addCheck(new OrderingService(this));
		addCheck(new CheckService(this));
		addCheck(new CTLCheckService(this));
	}

	public ITypeDeclaration getType() {
		return type;
	}

	public Iterator<AbstractCheckService> iterator() {
		return services.iterator();
	}

	public void addCheck(AbstractCheckService checkService) {
		services.add(checkService);
		checkService.addObserver(this);
		notifyObservers();
	}

	public void update() {
		notifyObservers();
	}

	public Orders getOrders() {
		for (IServiceResultProvider asc : this) {
			if (asc instanceof OrderingService) {
				OrderingService os = (OrderingService) asc;
				return os.getOrders();
			}
		}
		return new Orders();
	}

	public CTLFormulaDescription findCtlFormula(String formName) {
		for (IServiceResultProvider asc : this) {
			if (asc instanceof CTLCheckService) {
				CTLCheckService os = (CTLCheckService) asc;
				for (CTLFormulaDescription cfd : os.getFormulae()) {
					if (cfd.getName().equals(formName)) {
						return cfd;
					}
				}
				return null;
			}
		}
		return null;
	}

	public void addCTLFormula(String name, String formula, String comments) {
		for (IServiceResultProvider asc : this) {
			if (asc instanceof CTLCheckService) {
				CTLCheckService os = (CTLCheckService) asc;
				os.addFormula(name, formula, comments);
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<CTLFormulaDescription> getCTLFormulas() {
		for (IServiceResultProvider asc : this) {
			if (asc instanceof CTLCheckService) {
				CTLCheckService os = (CTLCheckService) asc;
				return os.getFormulae();
			}
		}
		return Collections.EMPTY_LIST;
	}

	public List<String> getCtlFomulaNames() {
		List<String> toret = new ArrayList<String>();
		for (CTLFormulaDescription form : getCTLFormulas()) {
			toret.add(form.getName());
		}
		return toret;
	}

}
