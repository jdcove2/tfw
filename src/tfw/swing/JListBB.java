/*
 * The Framework Project Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; witout even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package tfw.swing;

import javax.swing.JList;
import tfw.swing.list.SelectionAndListCommit;
import tfw.swing.list.SelectionInitiator;
import tfw.tsm.Branch;
import tfw.tsm.BranchBox;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ila.IntIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class JListBB extends JList implements BranchBox
{
	private final Branch branch;

	public JListBB(String name, ObjectIlaECD listECD,
			ObjectIlaECD selectedItemsECD, IntIlaECD selectedIndexesECD,
			BooleanECD enabledECD)
	{
		this(new Branch(name), listECD, selectedItemsECD, selectedIndexesECD,
			enabledECD);
	}

	public JListBB(Branch branch, ObjectIlaECD listECD,
		ObjectIlaECD selectedItemsECD, IntIlaECD selectedIndexesECD,
		BooleanECD enabledECD)
	{
		this.branch = branch;

		SelectionInitiator selectionInitiator = new SelectionInitiator(
			"JListBB", selectedItemsECD, selectedIndexesECD, this);

		addListSelectionListener(selectionInitiator);
		branch.add(selectionInitiator);

		SelectionAndListCommit selectionAndListCommit = new SelectionAndListCommit(
			"JListBB", listECD, selectedItemsECD, selectedIndexesECD,
			new Initiator[] { selectionInitiator }, this);

		branch.add(selectionAndListCommit);
	}

	public final Branch getBranch()
	{
		return (branch);
	}
}