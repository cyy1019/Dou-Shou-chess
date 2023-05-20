package controller;

import model.Cell;
import model.Step;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


    /*TODO:the file saving the game should include the current chessboard, the previous move, the current side to play*/
/*TODO：读入进来之后前端要重新画棋盘
/*TODO:把cell和step存在同一个文件里，或者不用输入文档，默认存在哪个路径下？？？*/
    /*TODO：如果做用户登录系统，那根据用户决定存进的文档，但路径要是确定的*/
    public class Loading {//要序列化什么，整个cell的二维数组grid和previous step？？？
        public static void serializeCell(Cell[][] grid, String filename) throws IOException{//只序列化了一个cell
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(filename));//filename可以直接输入统一路径下的文件名
            objectOutputStream.writeObject(grid);
            objectOutputStream.close();
        }
        public static Cell[][] deserializeCell(String filename) throws IOException,ClassNotFoundException{
            ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(filename));
            Cell[][] grid = (Cell[][]) objectinputstream.readObject();//读入进来直接就是一个cell的二维数组，不用再initgrid
            objectinputstream.close();
            return grid;
        }//从文件中读入grid后将棋盘返回
        public static void serializeStep(List<Step> stepSet,String filename) throws IOException{
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(stepSet);
            objectOutputStream.close();

        }
        public static List<Step> deserializeStep(String filename) throws IOException,ClassNotFoundException{
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(new FileInputStream(filename));
            List<Step> stepSet= (ArrayList<Step>) objectInputStream.readObject();
            objectInputStream.close();
            return stepSet;

        }}




