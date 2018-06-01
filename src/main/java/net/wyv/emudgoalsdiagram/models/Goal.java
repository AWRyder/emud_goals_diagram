package net.wyv.emudgoalsdiagram.models;

import java.util.LinkedList;
import java.util.List;

public class Goal {
    private Integer id;
    private String name;
    private Integer points;
    private Integer rewardCost;
    private Boolean isReward;

    private List<Integer> dependencies;

    public Goal() {
        this.dependencies = new LinkedList<>();
        this.points = 0;
        this.rewardCost = 0;
        this.isReward = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRewardCost() {
        return rewardCost;
    }

    public void setRewardCost(Integer rewardCost) {
        this.rewardCost = rewardCost;
    }

    public Boolean getReward() {
        return isReward;
    }

    public void setReward(Boolean reward) {
        isReward = reward;
    }
}
