package com.htoja.mifik.htoja.control;

import android.content.Context;

import com.htoja.mifik.htoja.data.TeamsSet;
import com.htoja.mifik.htoja.utils.Storage;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class TeamGameManager {
    private static TeamGameManager ourInstance = new TeamGameManager();
    private TeamsSet currentSet;
    private String currentTeam;
    private int rounds = 1;
    private int wordPointer;

    public static TeamGameManager getInstance() {
        return ourInstance;
    }

    private TeamGameManager() {
    }

    public void startNewSet(List<String> teams, int targetWords, int seconds, boolean fine,
                            List<String> categories, List<String> words, boolean seeScreen) {
        this.currentTeam = teams.get(0);
        LinkedHashMap<String, Integer> teamResults = new LinkedHashMap<>(teams.size());
        for (String team : teams) {
            teamResults.put(team, 0);
        }
        this.currentSet = new TeamsSet(teamResults, teams, targetWords, seconds, fine, categories, words, seeScreen);
        rounds = 1;
        wordPointer = 0;
    }

    public String getCurrentTeam() {
        return this.currentTeam;
    }


    public TeamsSet getCurrentTeamSet() {
        return this.currentSet;
    }

    public int getRoundTime() {
        return currentSet.getRoundTime();
    }

    public int getPointsToWin() {
        return currentSet.getPointsToWin();
    }

    public String firstTeam() {
        currentTeam = currentSet.getTeams().get(0);
        return currentTeam;
    }

    public void nextTeam() {
        int i = getTeams().indexOf(currentTeam);
        i++;
        if (i >= getTeams().size()) {
            nextRound();
            i = 0;
        }
        currentTeam = getTeams().get(i);
    }

    private List<String> getTeams() {
        return currentSet.getTeams();
    }

    private void nextRound() {
        int max = currentSet.getPointsToWin();
        for (Map.Entry<String, Integer> entry : getTeamResults().entrySet()) {
            if (entry.getValue() >= max) {
                currentSet.setEnded(true);
                currentSet.setVictorian(entry.getKey());
                max = entry.getValue();
            }
        }
        rounds++;
    }

    public String getVictorian() {
        return currentSet.getVictorian();
    }

    public int getRound() {
        return this.rounds;
    }

    public void addCurrentTeamPoints(int points) {
        int currentPoints = getTeamResults().get(currentTeam);
        getTeamResults().put(currentTeam, currentPoints + points);
    }

    public boolean hasStarted() {
        return currentSet != null && !hasEnded();
    }

    public boolean hasEnded() {
        return currentSet.isEnded();
    }

    public boolean hasFine() {
        return currentSet.isFine();
    }

    public Map<String, Integer> getTeamResults() {
        return this.currentSet.getTeamResults();
    }

    public void startNewSet(TeamsSet set, String currentTeam, int round, int pointer) {
        this.currentTeam = currentTeam;
        this.rounds = round;
        this.currentSet = set;
        this.wordPointer = pointer;
    }

    public String nextWord() {
        String s = this.currentSet.getWords().get(wordPointer);
        wordPointer++;
        if (this.currentSet.getWords().size() <= wordPointer) {
            //this.currentSet.setEnded(true);

            wordPointer = 0;
            Collections.shuffle(this.currentSet.getWords(), new Random(System.nanoTime()));

        }
        return s;
    }

    public void end(Context ctx) {
        this.currentSet.setEnded(true);
        Storage.saveCurrentTeamState(ctx);
    }

    public List<String> getCategories() {
        return this.currentSet.getCategories();
    }

    public List<String> getWords() {
        return this.currentSet.getWords();
    }


    public List<String> getLeftWords() {
        return this.currentSet.getWords().subList(wordPointer, this.currentSet.getWords().size() - 1);
    }

    public int getPointer() {
        return this.wordPointer;
    }

    public boolean isSeeScreen() {
        return this.currentSet.isSeeScreen();
    }
}
