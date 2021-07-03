package org.ccs.uhcp;

import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TeamAllocator {
    static double k_singleGame = 0.2; // pp更新常数 越大说明单局游戏的表现影响越高
    static double k_wStreak = 1.05; // 连胜影响系数 越大说明连胜/连败影响越大
    public TeamAllocator(){

    }
    /**
     *  队伍分配影响因素:
     *  输出/承伤系数
     *  连胜/连败
     *
     *  玩家的输出/承伤标准化后和整局游戏所有玩家的平均值进行一个比较 得出一个分数作为技能分数
     *  连胜/连败会对当局技能分数乘以一个系数, 连胜系数为 1.x, 连败系数为0.9x
     *
     *
     * */




    public ArrayList<ArrayList<String>> matchMaking(Map<String,Double> players,int teamNums){
        ArrayList<ArrayList<String>> res = new ArrayList();
        for (int i=0;i<teamNums;i++){
            res.add(new ArrayList<>());
        }
        LinkedHashMap<String,Double> sortedPlayers = new LinkedHashMap<>();
        players.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sortedPlayers.put(x.getKey(),x.getValue()));

        int[] scores = new int[teamNums];
        int expectedPlayerNum = (players.keySet().size()/teamNums)+1;
        for (String player: sortedPlayers.keySet()){
            int weekTeam = 0;
            for (int i=0;i<teamNums;i++){
                if (scores[weekTeam]>scores[i]&&res.get(i).size()<){
                    weekTeam = i;
                }
            }
        }
        return null;
    }

    /**
     * 计算标准差和平均数
     *
     * */
    public double std(ArrayList<Double> data){

        double mean = mean(data);
        double t1 = 0;
        for (double i:data){
            double t2 = i-mean;
            t1 += t2*t2;
        }
        double std = Math.sqrt(t1);
        return std;
    }
    public double mean (ArrayList<Double> data){
        double sum = 0;
        for (double i:data){
            sum += i;
        }
        double mean = sum/data.size();
        return mean;
    }
    /**
     * 计算当前游戏技能分数等级
     * */
    public double SGPP (double personalData,double overallMean, double overallStd){
        double pp = (personalData - overallMean) / overallStd;
        return pp;

    }
    /**
     *  pp: 技能分数
     *  SGPP: 当前pp
     *  hisPP: 历史pp
     *  wStreak: 正为连胜 负为连败
     *  k: 影响系数
     *
     *
     * */
    public double updatePP (double hisPP, double SGPP, int wStreak){
         return (1-k_singleGame)*hisPP+(k_singleGame*SGPP*Math.pow(k_wStreak,(wStreak-1)));
    }
    public void shuffle(ArrayList<String> a){
        for (int i=a.size()-1;i>0;i--){
            int j = (int)(Math.random()*i);
            String t = a.get(i);
            a.set(i,a.get(j));
             a.set(j,t);
        }
    }
}
