/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game.mission;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author kelvy
 */
public class Mission implements BaseMission{
    public static int currentLevel = 0;
    public double hitPercentage;
    public DificultyLevel dificulty;
    public TypeOfMission type;
    public TypeOfProjectile projectile;
    public Point goalLocation;
    public boolean started = false;
    
    public Mission(){
        this.dificulty = DificultyLevel.VERY_EASY;
        this.type = TypeOfMission.HIT_THE_GOAL;
        this.projectile = TypeOfProjectile.PROJECTILE;
        this.setHit();
        this.setGoal();
    }
    
    public Mission(DificultyLevel dificulty, TypeOfMission type, TypeOfProjectile projectile){
        Random generator = new Random();
        this.dificulty = dificulty;
        this.type = type == TypeOfMission.ALL ? (generator.nextInt(10000) < 5000 ? TypeOfMission.HIT_THE_GOAL : TypeOfMission.AVOID_THE_GOAL) : type;
        this.projectile = projectile;
        this.setHit();
        this.setGoal();
    }
    @Override
    public BaseMission generatePreviousMission(){
        Mission.previousMission();
        return this.generateNextMission();
    }
    @Override
    public BaseMission generateNextMission() {
        Mission.nextMission();
        if(Mission.currentLevel <= 5){
            return new Mission(DificultyLevel.VERY_EASY, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.PROJECTILE);
        }else if(Mission.currentLevel <= 10){
            return new Mission(DificultyLevel.VERY_EASY, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.LONG_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 15){
            return new Mission(DificultyLevel.VERY_EASY, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.SHORT_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 20){
            return new Mission(DificultyLevel.EASY, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.ALL);
        }else if(Mission.currentLevel <= 25){
            return new Mission(DificultyLevel.EASY, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.PROJECTILE);
        }else if(Mission.currentLevel <= 30){
            return new Mission(DificultyLevel.EASY, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.LONG_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 35){
            return new Mission(DificultyLevel.EASY, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.SHORT_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 40){
            return new Mission(DificultyLevel.MODERATE, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.ALL);
        }else if(Mission.currentLevel <= 45){
            return new Mission(DificultyLevel.MODERATE, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.PROJECTILE);
        }else if(Mission.currentLevel <= 50){
            return new Mission(DificultyLevel.MODERATE, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.LONG_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 55){
            return new Mission(DificultyLevel.MODERATE, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.SHORT_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 60){
            return new Mission(DificultyLevel.HARD, TypeOfMission.HIT_THE_GOAL, TypeOfProjectile.ALL);
        }else if(Mission.currentLevel <= 65){
            return new Mission(DificultyLevel.HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.PROJECTILE);
        }else if(Mission.currentLevel <= 70){
            return new Mission(DificultyLevel.HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.LONG_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 75){
            return new Mission(DificultyLevel.HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.SHORT_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 80){
            return new Mission(DificultyLevel.VERY_HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.ALL);
        }else if(Mission.currentLevel <= 85){
            return new Mission(DificultyLevel.VERY_HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.PROJECTILE);
        }else if(Mission.currentLevel <= 90){
            return new Mission(DificultyLevel.VERY_HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.LONG_RANGE_ROCKET);
        }else if(Mission.currentLevel <= 95){
            return new Mission(DificultyLevel.VERY_HARD, TypeOfMission.AVOID_THE_GOAL, TypeOfProjectile.SHORT_RANGE_ROCKET);
        }else{
            return new Mission(DificultyLevel.VERY_HARD, TypeOfMission.ALL, TypeOfProjectile.ALL);
        }
        
    }
    
    public static void nextMission(){
        Mission.currentLevel++;
    }
    
    public static void previousMission(){
        Mission.currentLevel--;
    }
    
    public boolean evaluateMission(double hit){
        return this.type == TypeOfMission.HIT_THE_GOAL ?  hit >= this.hitPercentage : hit <= 1 - this.hitPercentage;
    }
    @Override
    public void setHit(){
        Random generator = new Random();
        switch(dificulty){
            case VERY_EASY:
                this.hitPercentage = generator.nextInt(500) / 1000.0;
                break;
            case EASY:
                this.hitPercentage = 0.5 + (generator.nextInt(100) / 1000.0);
                break;
            case MODERATE:
                this.hitPercentage = 0.6 + (generator.nextInt(100) / 1000.0);
                break;
            case HARD:
                this.hitPercentage = 0.7 + (generator.nextInt(100) / 1000.0);
                break;
            case VERY_HARD:
                this.hitPercentage = 0.8 + (generator.nextInt(200) / 1000.0);
                break;
        }
    }
    
    @Override
    public void setGoal() {
        Random generator = new Random();
        switch(dificulty){
            case VERY_EASY:
                this.goalLocation = !this.started ? new Point(600 + generator.nextInt(100), 300 + generator.nextInt(100) - 50) : this.goalLocation;
                break;
            case EASY:
                this.goalLocation = !this.started ?  (generator.nextInt(10000) > 9000 ? new Point(500 + generator.nextInt(200), 300 + generator.nextInt(200) - 100) : this.goalLocation) : this.goalLocation;
                break;
            case MODERATE:
                this.goalLocation = !this.started ?  (generator.nextInt(10000) > 6000 ? new Point(400 + generator.nextInt(300) - 150, 300 + generator.nextInt(300) - 150) : this.goalLocation) : this.goalLocation;
                break;
            case HARD:
                this.goalLocation = !this.started ?  (generator.nextInt(10000) > 3000 ? new Point(300 + generator.nextInt(400) - 200, 300 + generator.nextInt(400) - 200) : this.goalLocation) : this.goalLocation;
                break;
            case VERY_HARD:
                this.goalLocation = !this.started ?  (generator.nextInt(10000) > 0 ? new Point(300 + generator.nextInt(700) - 250, 300 + generator.nextInt(500) - 250) : this.goalLocation) : this.goalLocation;
                break;
        }
    }
}
