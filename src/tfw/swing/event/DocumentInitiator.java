/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.swing.event;

import	javax.swing.event.DocumentEvent;
import	javax.swing.event.DocumentListener;
import	javax.swing.text.BadLocationException;
import	tfw.tsm.Initiator;
import	tfw.tsm.ecd.ObjectECD;
import	tfw.tsm.ecd.StringECD;

public class DocumentInitiator extends Initiator implements DocumentListener
{
	private final StringECD textECD;
	
	public DocumentInitiator(String name, StringECD textECD)
	{
		super("DocumentInitiator["+name+"]",
			new ObjectECD[] {textECD});
		
		this.textECD = textECD;
	}
	
	public void changedUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
	
	public void insertUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
	
	public void removeUpdate(DocumentEvent e)
	{
		try
		{
			set(textECD,
				e.getDocument().getText(0, e.getDocument().getLength()));
		}
		catch(BadLocationException ble) {}
	}
}
