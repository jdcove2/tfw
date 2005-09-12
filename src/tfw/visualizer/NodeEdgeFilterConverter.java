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
package tfw.visualizer;

import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;
import tfw.visualizer.graph.GraphEdgeEitherClassFilter;
import tfw.visualizer.graph.GraphEdgeNeitherClassFilter;
import tfw.visualizer.graph.GraphNodeClassFilter;

public class NodeEdgeFilterConverter extends Converter
{
	private final GraphECD graphECD;
	private final BooleanECD showBranchesECD;
	private final BooleanECD showCommitsECD;
	private final BooleanECD showConvertersECD;
	private final BooleanECD showEventChannelsECD;
	private final BooleanECD showInitiatorsECD;
	private final BooleanECD showMultiplexedBranchesECD;
	private final BooleanECD showRootsECD;
	private final BooleanECD showSynchronizersECD;
	private final BooleanECD showTriggeredCommitsECD;
	private final BooleanECD showTriggeredConvertersECD;
	private final BooleanECD showValidatorsECD;
	private final BooleanECD showStructureEdgesECD;
	private final BooleanECD showDataFlowEdgesECD;
	private final GraphECD filteredGraphECD;
	
	public NodeEdgeFilterConverter(GraphECD graphECD, BooleanECD showBranchesECD,
		BooleanECD showCommitsECD, BooleanECD showConvertersECD,
		BooleanECD showEventChannelsECD, BooleanECD showInitiatorsECD,
		BooleanECD showMultiplexedBranchesECD, BooleanECD showRootsECD,
		BooleanECD showSynchronizersECD, BooleanECD showTriggeredCommitsECD,
		BooleanECD showTriggeredConvertersECD, BooleanECD showValidatorsECD,
		BooleanECD showStructureEdgesECD, BooleanECD showDataFlowEdgesECD,
		GraphECD filteredGraphECD)
	{
		super("FilterConverter",
			new EventChannelDescription[] {graphECD, showBranchesECD,
				showCommitsECD, showConvertersECD, showEventChannelsECD,
				showInitiatorsECD, showMultiplexedBranchesECD, showRootsECD,
				showSynchronizersECD, showTriggeredCommitsECD,
				showTriggeredConvertersECD, showValidatorsECD,
				showStructureEdgesECD, showDataFlowEdgesECD},
			null,
			new EventChannelDescription[] {filteredGraphECD});
		
		this.graphECD = graphECD;
		this.showBranchesECD = showBranchesECD;
		this.showCommitsECD = showCommitsECD;
		this.showConvertersECD = showConvertersECD;
		this.showEventChannelsECD = showEventChannelsECD;
		this.showInitiatorsECD = showInitiatorsECD;
		this.showMultiplexedBranchesECD = showMultiplexedBranchesECD;
		this.showRootsECD = showRootsECD;
		this.showSynchronizersECD = showSynchronizersECD;
		this.showTriggeredCommitsECD = showTriggeredCommitsECD;
		this.showTriggeredConvertersECD = showTriggeredConvertersECD;
		this.showValidatorsECD = showValidatorsECD;
		this.showStructureEdgesECD = showStructureEdgesECD;
		this.showDataFlowEdgesECD = showDataFlowEdgesECD;
		this.filteredGraphECD = filteredGraphECD;
	}
	
	protected void convert()
	{
		Graph graph = (Graph)get(graphECD);
		
		if (!((Boolean)get(showBranchesECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, BranchProxy.class);
		}
		if (!((Boolean)get(showCommitsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, CommitProxy.class);
		}
		if (!((Boolean)get(showConvertersECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, ConverterProxy.class);
		}
		if (!((Boolean)get(showEventChannelsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, EventChannelProxy.class);
		}
		if (!((Boolean)get(showInitiatorsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, InitiatorProxy.class);
		}
		if (!((Boolean)get(showMultiplexedBranchesECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, MultiplexedBranchProxy.class);
		}
		if (!((Boolean)get(showRootsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, RootProxy.class);
		}
		if (!((Boolean)get(showSynchronizersECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, SynchronizerProxy.class);
		}
		if (!((Boolean)get(showTriggeredCommitsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, TriggeredCommitProxy.class);
		}
		if (!((Boolean)get(showTriggeredConvertersECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, TriggeredConverterProxy.class);
		}
		if (!((Boolean)get(showValidatorsECD)).booleanValue())
		{
			graph = GraphNodeClassFilter.create(graph, ValidatorProxy.class);
		}
		if (!((Boolean)get(showStructureEdgesECD)).booleanValue())
		{
			graph = GraphEdgeNeitherClassFilter.create(graph, EventChannelProxy.class);
		}
		if (!((Boolean)get(showDataFlowEdgesECD)).booleanValue())
		{
			graph = GraphEdgeEitherClassFilter.create(graph, EventChannelProxy.class);
		}

		set(filteredGraphECD, graph);
	}
}