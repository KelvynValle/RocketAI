/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Game.mission;

/**
 *
 * @author kelvyn valle
 */
public interface BaseMission {
    enum DificultyLevel{
        VERY_EASY,
        EASY,
        MODERATE,
        HARD,
        VERY_HARD
    }
    enum TypeOfMission{
        HIT_THE_GOAL,
        AVOID_THE_GOAL,
        ALL
    }
    enum TypeOfProjectile{
        ALL,
        ROCKETS,
        PROJECTILE,
        SHORT_RANGE_ROCKET,
        LONG_RANGE_ROCKET
    }
    
    public abstract void setGoal();
    public abstract BaseMission generateNextMission();
    public abstract BaseMission generatePreviousMission();
    public abstract void setHit();
}
