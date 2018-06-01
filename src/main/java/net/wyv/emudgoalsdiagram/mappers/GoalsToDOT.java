package net.wyv.emudgoalsdiagram.mappers;


import net.wyv.emudgoalsdiagram.models.Goal;

import java.util.Map;
import java.util.function.Function;

public class GoalsToDOT implements Function<Map<Integer,Goal>,String> {

    @Override
    public String apply(Map<Integer,Goal> goalMap) {
        StringBuilder dotStringBuilder = new StringBuilder();

        dotStringBuilder.append("digraph goals {\n");

        for (Goal goal : goalMap.values()) {
            dotStringBuilder.append("\t\""+goal.getId()+"\"");
            if ( goal.getRewardCost() > 0 ) {
                dotStringBuilder.append("[label=\""+goal.getName()+"("+goal.getRewardCost()+")\"]");
                dotStringBuilder.append("[fillcolor=cyan,style=filled]");
            } else {
                dotStringBuilder.append("[label=\""+goal.getName()+"("+goal.getPoints()+")\"]");
            }
            dotStringBuilder.append("\n");
            for (Integer id : goal.getDependencies()) {
                Goal dependency = goalMap.get(id);
                dotStringBuilder.append("\t\""+dependency.getId()+"\" -> \""+goal.getId()+"\"");
                if ( goal.getRewardCost() > 0 ) {
                    dotStringBuilder.append("[color=\"cyan\",style=dashed]");
                }
                dotStringBuilder.append("\n");
            }
        }

        dotStringBuilder.append("}\n");


        return dotStringBuilder.toString();
    }
}
