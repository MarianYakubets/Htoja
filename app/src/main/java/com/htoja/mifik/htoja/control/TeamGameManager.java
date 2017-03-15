package com.htoja.mifik.htoja.control;

import com.htoja.mifik.htoja.data.TeamsSet;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class TeamGameManager {
    private static TeamGameManager ourInstance = new TeamGameManager();

    private TeamsSet currentSet;
    private String currentTeam;
    private List<String> teams;

    public static TeamGameManager getInstance() {
        return ourInstance;
    }

    private TeamGameManager() {
    }

    public void startNewSet(List<String> teams) {
        this.teams = teams;
        this.currentTeam = teams.get(0);

        HashMap<String, Integer> teamMap = new LinkedHashMap<>(teams.size());
        for (String team : teams) {
            teamMap.put(team, 0);
        }
        this.currentSet = new TeamsSet(teamMap);
    }

    public String getCurrentTeam() {
        return this.currentTeam;
    }

    public String nextTeam() {
        int i = teams.indexOf(currentTeam);
        i++;
        if (i >= teams.size()) {
            i = 0;
        }
        currentTeam = teams.get(i);
        return currentTeam;
    }

    public void addCurrentTeamPoints(int points) {
        int currentPoints = currentSet.getTeamResults().get(currentTeam);
        currentSet.getTeamResults().put(currentTeam, currentPoints + points);
    }

    public Map<String, Integer> getTeamResults() {
        return currentSet.getTeamResults();
    }
}