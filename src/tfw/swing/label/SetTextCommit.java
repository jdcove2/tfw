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
package tfw.swing.label;

import java.awt.EventQueue;
import javax.swing.JLabel;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StringECD;

public class SetTextCommit extends Commit
{
	private final StringECD textECD;
	private final JLabel label;
	
	public SetTextCommit(String name, StringECD textECD, JLabel label,
		Initiator[] initiators)
	{
		super("SetTextCommit["+name+"]",
			new EventChannelDescription[] {textECD},
			null,
			initiators);
		
		this.textECD = textECD;
		this.label = label;
	}
	
	protected void commit()
	{
		final String text = (String)get(textECD);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				label.setText(text);				
			}
		});
	}
}