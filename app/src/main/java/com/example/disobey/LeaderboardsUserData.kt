package com.example.disobey

public class LeaderboardsUserData {
    var name: String? = null
    var disobeySteps: Int? = null

    public constructor(name: String?, disobeySteps: Int?) {
        this.name = name
        this.disobeySteps = disobeySteps
    }
    public constructor()
}