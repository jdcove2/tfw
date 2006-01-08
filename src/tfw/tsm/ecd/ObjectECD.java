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
package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.ValueCodec;
import tfw.value.ValueConstraint;

/**
 * A <code>java.lang.Object</code> event channel descritpion. This is the base
 * class for all stateful event channel descriptions.
 */
public class ObjectECD extends EventChannelDescription
{
    /**
     * Creates an event channel description with the specified name.
     * 
     * @param name
     *            the name of the event channel.
     */
    public ObjectECD(String name)
    {
        super(name, ClassValueConstraint.OBJECT, null);
    }

    /**
     * Creates an event channel description with the specified attributes. This
     * constructor is made available to sub-classing purposes only.
     * 
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the event channel.
     */
    protected ObjectECD(String eventChannelName, ValueConstraint constraint,
            ValueCodec codec)
    {
        super(eventChannelName, constraint, codec, true, true);
    }

    /**
     * Creates an event channel description with the specified attributes. This
     * constructor is intentionally package private. 
     * 
     * @param eventChannelName
     *            the name of the event channel.
     * @param constraint
     *            the value constraint for the evnet channel.
     * @param codec
     *            the codec for the event channel values. <code>null</code> is
     *            a valid value.
     * @param fireOnConnect
     *            flag indicating whether the event channel fires state when a
     *            new sink is connected.
     * @param rollbackParticipant
     *            flag indicating whether the event channel participates in
     *            transaction rollbacks.
     */
    ObjectECD(String eventChannelName, ValueConstraint constraint,
            ValueCodec codec, boolean fireOnConnect, boolean rollbackParticipant)
    {
        super(eventChannelName, constraint, codec, fireOnConnect,
                rollbackParticipant);
    }
}
