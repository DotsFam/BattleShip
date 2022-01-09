package com.company;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){

        int[][] field1=new int[6][6];   //создание поля для 1-го игрока
        int[][] fieldfire1=new int[6][6];   //создание поля для выстрелов 1-го игрока
        boolean fire1=true;
        int igrok=1;
        System.out.println("===== Приветствуем Вас в консольной мини-игре МОРСКОЙ БОЙ 6х6 =====");
        System.out.println("              для удобства, расширьте консольное окно ");
        System.out.println("--------------------    Привила игры    ---------------------");
        System.out.println("--- все действия производятся путем ввода команд в консольное окно");
        System.out.println("--- 2 игрока по очереди создают корабли:сначала 2 двухбалубных, затем 3 однопалубных");
        System.out.println("--- после создания кораблей первый игрок производит выстрел путем ввода команды в консольное окно");
        System.out.println("--- если игрок попал, то он продолжает стрелять, иначе - право хода предоставляется другому игроку");
        System.out.println("--- игра продолжается до тех пор, пока одним из игроков не будут потоплены все корабли соперника");
        System.out.println("--- обозначения на поле: 0-пустое поле, 1-корабль, 5-ранил, 7-убил, 9-промазал");
        System.out.println("                                  УДАЧИ!!!");
        System.out.println("чтобы начать игру, введите в консольное окно любой символ и нажмите ENTER");

        Scanner scanner = new Scanner(System.in);
        String ship = scanner.nextLine();
        for(int i=1;i<20;i++){
            System.out.println("");
        }
        OutputField(field1,fieldfire1);              //вывод поля для 1 игрока
        System.out.println("Создает корабли 1-й игрок");

        CreateShip(field1,fieldfire1,2,2,20); //создание 2-палубных кораблей 1 игрока= 2 двухпалобных каждый из которых состоят из 2 однопалубных
        CreateShip(field1,fieldfire1,1,3,10); //создание 3 однопалубных кораблей 1 игрока
        System.out.println("Создание кораблей первым игркоком закончено");
        System.out.println("");
        int[][] field2=new int[6][6];  //создание поля для 2-го игрока
        int[][] fieldfire2=new int[6][6]; //создание поля для выстрелов 2-го игрока

        OutputField(field2,fieldfire2);
        System.out.println("Создает корабли 2-й игрок");
        CreateShip(field2,fieldfire2,2,2,20); //создание 2-палубных кораблей 2 игрока= 2 двухпалобных кажд
        CreateShip(field2,fieldfire2,1,3,10); //создание 3 однопалубных кораблей 1 игрока
        System.out.println("Создание кораблей вторым игркоком закончено");
        System.out.println("");
        while(fire1) {
            if (igrok==1) {  //стреляет первый игрок
                Fire(igrok, field2, fieldfire1, field1);//первое поле соперника, второе-поле выстрелов,третье свое
                igrok = 2;
                int countship=0;
                for (int i = 0; i < 6; i++) {//  проверяем остались ли корабли
                    for (int k = 0; k < 6; k++) {
                        if (field2[i][k] == 1) countship++;
                    }
                }
                if (countship == 0) { //  если кораблей нет то прекращаем стрелять
                    fire1 = false;}
            }
            else { Fire(igrok,field1, fieldfire2, field2); //стреляет второй игрок
                igrok=1;
                int countship=0;
                for (int i = 0; i < 6; i++) {//  проверяем остались ли корабли
                    for (int k = 0; k < 6; k++) {
                        if (field1[i][k] == 1) countship++;
                    }
                }
                if (countship == 0) {//  если кораблей нет то прекращаем стрелять
                    fire1 = false;}
            }
            //OutputField(field1,fieldfire1);
        }
    }

    public static void OutputField(int[][] massiv,int[][] massiv1) {
        System.out.println("   Ваше поле      Поле выстрелов");
        String[] stroka1 = new String[7];
        stroka1[0] = "   ";
        stroka1[1] = "A ";
        stroka1[2] = "B ";
        stroka1[3] = "C ";
        stroka1[4] = "D ";
        stroka1[5] = "E ";
        stroka1[6] = "F ";

        for (int i = 0; i <= 6; i++) {
            System.out.print(stroka1[i]); // буквы для 1 поля
        }
        System.out.print("   ");
        for (int i = 0; i <= 6; i++) {
            System.out.print(stroka1[i]); // буквы для 2 поля
        }
        System.out.println();

        int[] stolbec0=new int[6];
        for (int t = 0; t < 6; t++) {
            stolbec0[t] = t + 1;     //создание ОТДЕЛЬНОГО массива для нулевого столбца с числами от 0 до 6
        }

        for (int k = 0; k <= 5; k++) {
            //if (k==9) System.out.print(stolbec0[k]);   //для цифр в столбе от 1 до 6 добавляется пробел
            System.out.print(stolbec0[k]+" ");  //вывод первого элемента первой строки
            for (int p = 0; p <= 5; p++) {
                System.out.print(" "+ massiv[k][p]);   //вывод остальных 6 элементов 1 строки
            }

            System.out.print("    "+stolbec0[k]+" ");  //вывод первого элемента первой строки для 2 поля
            for (int p = 0; p <= 5; p++) {
                System.out.print(" "+ massiv1[k][p]);   //вывод остальных 6 элементов 1 строки 2 поля
            }

            System.out.println();
        }
        System.out.println();



    }



    public static void CreateShip(int[][] massiv2,int[][] massiv10,int amount2ships, int amount1ships,int schetchik3) { //ваше поле и поле выстрелов
        boolean go = true;
        int schetchik2 = 1;   // для двойных кораблей
        String ship1[] = {"u"};  // после ввода цикла while(vvod==1) дальше прога не видит переменные
        String ship2 = "u";      //и поэтому пришлось их инициализировать
        int stroka = 0;
        int stolb = 0;
        String s1 = "A";
        String s2 = "B";
        String s3 = "C";
        String s4 = "D";
        String s5 = "E";
        String s6 = "F";

        String s11 = "a";
        String s12 = "b";
        String s13 = "c";
        String s14 = "d";
        String s15 = "e";
        String s16 = "f";
        while (schetchik2 <= amount2ships) {//для 2 палубных кораблей, но для 1палуб-х 1 раз должен же сделать
            int schetchik = 1;   // для одинарных
            int stolb2 = 10;    //для 2-палубных кораблей чтобы не совпадало с 1
            int stroka2 = 10;
            while (schetchik <= amount1ships) {//количество однопалубных кораблей

                int vvod = 1;
                while (vvod == 1) {//цикл для проверки корректного ввода, если ввод некорр-й то vvod=0
                    vvod = 0;
                    String kor="однопалубный корабль";
                    if ((amount2ships == 2) && (schetchik == 1)) kor="первую часть двухпалубного корабля";
                    if ((amount2ships == 2) && (schetchik == 2)) kor="вторую часть двухпалубного корабля";
                    System.out.println("Введите в консольное окно "+kor+", например B3");
                    Scanner scanner = new Scanner(System.in);
                    String ship = scanner.nextLine();
                    ship1 = ship.split(""); //разбитие текста по элементам и занесение в массив
                    ship2 = ship1[0];//шип2=букве корабля
                    if ((ship1.length == 2) &&
                            ((ship2.equals("a")) || (ship2.equals("A")) || (ship2.equals("b")) || (ship2.equals("B")) ||
                                    (ship2.equals("c")) || (ship2.equals("C")) || (ship2.equals("d")) || (ship2.equals("D")) || (ship2.equals("e")) ||
                                    (ship2.equals("E")) || (ship2.equals("f")) || (ship2.equals("F"))) &&
                            ((ship1[1].equals("1")) || (ship1[1].equals("2")) || (ship1[1].equals("3")) || (ship1[1].equals("4")) ||
                                    (ship1[1].equals("5")) || (ship1[1].equals("6")))) { //если вводимые символы правильные то

                        stroka = Integer.parseInt(ship1[1]);
                        stroka = stroka - 1;
                        if ((s1.equals(ship2)) || (s11.equals(ship2)))
                            stolb = 0;  //сравнение введенной буквы чтобы понять в какой столбец записывать
                        if ((s2.equals(ship2)) || (s12.equals(ship2))) stolb = 1;
                        if ((s3.equals(ship2)) || (s13.equals(ship2))) stolb = 2;
                        if ((s4.equals(ship2)) || (s14.equals(ship2))) stolb = 3;
                        if ((s5.equals(ship2)) || (s15.equals(ship2))) stolb = 4;
                        if ((s6.equals(ship2)) || (s16.equals(ship2))) stolb = 5;

                        if (massiv2[stroka][stolb] == 1) {
                            System.out.println();
                            System.out.println("Создайте корабль в другом месте1");
                            System.out.println();
                            vvod = 1;
                        } else {
                            if ((stroka == 0) || (stolb == 0 || (stroka == 5) || stolb == 5)) { //если элменет находится на краю поля
                                //11111111111111111
                                if ((stroka == 0) && (stolb == 0)) {   //если введен А1, то проверяем вокруг все элементы
                                    if (((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb + 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka][stolb + 1] == 1)&&((stroka !=stroka2)||((stolb+1)!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте2");
                                        System.out.println();
                                        vvod = 1;
                                    } //и если рядом эл-т равен 1 то повторяем ввод
                                    else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1; //если это однопалуб то присваиваем 1
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте3");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте4");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте5");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //22222222222222222222222222
                                else if ((stroka == 5) && (stolb == 0)) {  //если введен А6, то проверяем вокруг все элементы
                                    if (((massiv2[stroka][stolb + 1] == 1)&&((stroka!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb + 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb] == 1)&&(((stroka - 1)!=stroka2)||(stolb!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте6");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте7");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте8");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте9");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //333333333333333333
                                else if ((stroka == 5) && (stolb == 5)) { //если введен F6, то проверяем вокруг все элементы
                                    if (((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb] == 1)&&(((stroka - 1)!=stroka2)||(stolb!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте10");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте11");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте12");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте13");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //4444444444444444444444
                                else if ((stroka == 0) && (stolb == 5)) { //если введен F1, то проверяем вокруг все элементы
                                    if (((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb - 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте14");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте15");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте16");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте17");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //5555555555555555555555
                                else if ((stolb == 0) && (stroka != 0) && (stroka != 5)) { //если введен эл-т из первого столбца и он не 1 и не 6
                                    if (((massiv2[stroka - 1][stolb] == 1)&&(((stroka -1)!=stroka2)||(stolb!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb + 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka][stolb + 1] == 1)&&((stroka!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb + 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте18");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте19");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте20");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте21");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //6666666666666666666666

                                else if ((stolb == 5) && (stroka != 0) && (stroka != 5)) { System.out.println(massiv2[stroka - 1][stolb - 1]);//если введен
                                    if (((massiv2[stroka - 1][stolb] == 1)&&(((stroka - 1)!=stroka2)||(stolb!=stolb2)))       //эл-т из последнего столбца и он не 1 и не 6
                                            || ((massiv2[stroka - 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте22");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте23");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте24");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте25");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //77777777777777777777777
                                else if ((stroka == 0) && (stolb != 0) && (stolb != 5)) { //если введен эл-т из первой строки и он не 1 и не 6
                                    if (((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb - 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))
                                            || ((massiv2[stroka + 1][stolb + 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka][stolb + 1] == 1)&&((stroka!=stroka2)||((stolb+1)!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте26");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте27");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте28");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте29");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                                //8888888888888888888888888
                                else if ((stroka == 5) && (stolb != 0) && (stolb != 5)) { //если введен эл-т из последней строки и он не 1 и не 6
                                    if (((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb] == 1)&&(((stroka - 1)!=stroka2)||(stolb!=stolb2)))
                                            || ((massiv2[stroka - 1][stolb + 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb+1)!=stolb2)))
                                            || ((massiv2[stroka][stolb + 1] == 1)&&((stroka!=stroka2)||((stolb+1)!=stolb2)))) {
                                        System.out.println();
                                        System.out.println("Создайте корабль в другом месте30");
                                        System.out.println();
                                        vvod = 1;
                                    } else {
                                        if (amount2ships == 1) massiv2[stroka][stolb] = 1;
                                        if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                        if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                            if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                                if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                    if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте31");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                else {
                                                    if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                        if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                            massiv2[stroka][stolb] = 1;
                                                            //то проверяем смежность с 1 эл-том
                                                        else {
                                                            System.out.println();
                                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте32");
                                                            System.out.println();
                                                            vvod = 1;
                                                        }
                                                    }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                }
                                            }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            else {
                                                System.out.println();
                                                System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте33");
                                                System.out.println();
                                                vvod = 1;
                                            }
                                        }//если идет ввод второй части двойного корабля
                                    }
                                }
                            } //если элемент находился на краю поля
//*****************************************
                            else if ((stroka != 0) || (stolb != 0) || (stroka != 5) || (stolb != 5)) {  //если элемент НЕ НАХОДИТСЯ на краю таблицы
                                if (((massiv2[stroka + 1][stolb] == 1)&&(((stroka + 1)!=stroka2)||(stolb!=stolb2)))
                                        || ((massiv2[stroka + 1][stolb + 1] == 1)&&(((stroka + 1)!=stroka2)||((stolb+1)!=stolb2)))
                                        || ((massiv2[stroka][stolb + 1] == 1)&&((stroka!=stroka2)||((stolb+1)!=stolb2)))
                                        || ((massiv2[stroka - 1][stolb + 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb+1)!=stolb2)))
                                        || ((massiv2[stroka - 1][stolb] == 1)&&(((stroka - 1)!=stroka2)||(stolb!=stolb2)))
                                        || ((massiv2[stroka - 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))
                                        || ((massiv2[stroka][stolb - 1] == 1)&&((stroka!=stroka2)||((stolb-1)!=stolb2)))
                                        || ((massiv2[stroka + 1][stolb - 1] == 1)&&(((stroka - 1)!=stroka2)||((stolb-1)!=stolb2)))) {
                                    System.out.println();
                                    System.out.println("Создайте корабль в другом месте34");
                                    System.out.println();
                                    vvod = 1;
                                } else {
                                    if (amount2ships == 1) massiv2[stroka][stolb] = 1; //если идет ввод одинарных кораблей
                                    if ((amount2ships == 2) && (schetchik == 1)) massiv2[stroka][stolb] = 1; //если первая часть 2палуб кор-ля
                                    if ((amount2ships == 2) && (schetchik == 2)) {   //если идет ввод второй части двойного корабля
                                        if ((stroka == stroka2) || (stolb == stolb2)) {  //если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                            if (stroka == stroka2) {//если 2-й эл-т в той же строке что и наш 1-й эл-т
                                                if (((stolb - 1) == stolb2) || ((stolb + 1) == stolb2))
                                                    massiv2[stroka][stolb] = 1;
                                                    //то проверяем смежность с 1 эл-том
                                                else {
                                                    System.out.println();
                                                    System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте35");
                                                    System.out.println();
                                                    vvod = 1;
                                                }
                                            } //если 2-й эл-т в той же строке что и наш 1-й эл-т
                                            else {
                                                if (stolb == stolb2) { //если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                                    if (((stroka - 1) == stroka2) || ((stroka + 1) == stroka2))
                                                        massiv2[stroka][stolb] = 1;
                                                        //то проверяем смежность с 1 эл-том
                                                    else {
                                                        System.out.println();
                                                        System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте36");
                                                        System.out.println();
                                                        vvod = 1;
                                                    }
                                                }//если 2-й эл-т в том же столбце что и наш 1-й эл-т
                                            }
                                        }//если 2-й эл-т в той же строке или столбце что и наш 1-й эл-т
                                        else {
                                            System.out.println();
                                            System.out.println("Элементы двойного корабля должны быть рядом. Создайте корабль в другом месте37");
                                            System.out.println();
                                            vvod = 1;
                                        }
                                    }//если идет ввод второй части двойного корабля
                                }
                            }

                            OutputField(massiv2, massiv10);//вывод полей перед выстрелом

                        } // введенный символ не тот же самый и тогда иначе
                    }   //если введенные символы были правильные
                        else { //если символы введены неправильно то
                                System.out.println();
                                System.out.println("Некорректный ввод. Ввод должен состоять только из 2 символов.");
                                System.out.println("Первым должен быть символ из ряда:A B C D E F, вторым из ряда: 1 2 3 4 5 6");
                                vvod = 1;
                                System.out.println();
                                OutputField(massiv2, massiv10);//вывод полей перед выстрелом
                            }
                            //OutputField(massiv1);

                        }
                        if ((amount2ships == 2) && (schetchik == 1)) { // если создаются двухпалубные корабли и создана первая часть, то
                            stroka2 = stroka;                  //для 2палубных кораблей запоминаем где записана 1 часть корабля
                            stolb2 = stolb;
                        }
                        schetchik++;
                    }
                    schetchik2++;
                }
            }



    public static void Fire(int igrok,int[][]massiv3,int[][] massiv4,int[][]massiv5) { //массив3 это поле игрока,по кот стреляют
        int strokavistrela=0;  //массив4 это поле выстрелов стреляющего игрока, массив5 это поле стреляющего игрока
        int stolbvistrela=0;
        boolean fire=true;
        boolean proverkanavvod=true;
        String s1="A";
        String s2="B";
        String s3="C";
        String s4="D";
        String s5="E";
        String s6="F";

        String s11="a";
        String s12="b";
        String s13="c";
        String s14="d";
        String s15="e";
        String s16="f";

        while (fire) {
            while (proverkanavvod) {
                proverkanavvod = false;
                System.out.println("Игрок" + igrok + " произведите выстрел, например А2");
                System.out.println("5-ранение   7-попадание   9-промах");
                System.out.println();
                OutputField(massiv5, massiv4);    //вывод полей перед выстрелом
                Scanner scanner = new Scanner(System.in);
                String vistrel = scanner.nextLine();
                String[] vistrel1 = vistrel.split(""); //разбитие текста по элементам и занесение в массив
                String vistrel2 = vistrel1[0];  //шип2=букве корабля

                if ((vistrel1.length == 2) &&
                        ((vistrel2.equals("a")) || (vistrel2.equals("A")) || (vistrel2.equals("b")) || (vistrel2.equals("B")) ||
                                (vistrel2.equals("c")) || (vistrel2.equals("C")) || (vistrel2.equals("d")) || (vistrel2.equals("D")) || (vistrel2.equals("e")) ||
                                (vistrel2.equals("E")) || (vistrel2.equals("f")) || (vistrel2.equals("F"))) &&
                        ((vistrel1[1].equals("1")) || (vistrel1[1].equals("2")) || (vistrel1[1].equals("3")) || (vistrel1[1].equals("4")) ||
                                (vistrel1[1].equals("5")) || (vistrel1[1].equals("6")))) {

                    strokavistrela = Integer.parseInt(vistrel1[1]) - 1; //для остальных случаем strok=второму элементу массива то есть числу
                    if ((s1.equals(vistrel2)) || (s11.equals(vistrel2))) stolbvistrela = 0;  //сравнение введенной буквы чтобы понять в какой столбец записывать
                    if ((s2.equals(vistrel2)) || (s12.equals(vistrel2))) stolbvistrela = 1;
                    if ((s3.equals(vistrel2)) || (s13.equals(vistrel2))) stolbvistrela = 2;
                    if ((s4.equals(vistrel2)) || (s14.equals(vistrel2))) stolbvistrela = 3;
                    if ((s5.equals(vistrel2)) || (s15.equals(vistrel2))) stolbvistrela = 4;
                    if ((s6.equals(vistrel2)) || (s16.equals(vistrel2))) stolbvistrela = 5;


                    if (massiv3[strokavistrela][stolbvistrela] == 1) {  //если попадание
                        //1111111111111111111
                        if ((strokavistrela==0)&&(stolbvistrela==0)) {  //если элемент А1
                            if ((massiv3[strokavistrela][stolbvistrela + 1] == 1) || (massiv3[strokavistrela + 1][stolbvistrela] == 1)) {
                                System.out.print("Ранил! "); //если рядом элемент равен 1 значит ранил
                                massiv4[strokavistrela][stolbvistrela] = 5; //для поля выстреля записываем попадание
                                massiv3[strokavistrela][stolbvistrela] = 5; //для поля по которому стреляем записываем попадание
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7; //для поля выстреля записываем попадание
                                massiv3[strokavistrela][stolbvistrela] = 7; //для поля по которому стреляем записываем попадание
                                if (massiv3[strokavistrela][stolbvistrela + 1] == 5) { //если рядом был эл-т 5 то перезаписываем его в 7 для обоих массивов
                                    massiv3[strokavistrela][stolbvistrela + 1] = 7; massiv4[strokavistrela][stolbvistrela + 1] = 7;}
                                if (massiv3[strokavistrela + 1][stolbvistrela] == 5){
                                    massiv3[strokavistrela + 1][stolbvistrela] =7; massiv4[strokavistrela + 1][stolbvistrela] =7;}
                            }
                        }
                        //2222222222222222222
                        if ((strokavistrela==5)&&(stolbvistrela==0)) { //ЕСЛИ ЭЛЕМЕНТ А6
                            if ((massiv3[strokavistrela][stolbvistrela + 1] == 1) || (massiv3[strokavistrela - 1][stolbvistrela] == 1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela][stolbvistrela + 1] == 5){
                                    massiv3[strokavistrela][stolbvistrela + 1] = 7; massiv4[strokavistrela][stolbvistrela + 1] = 7;}
                                if (massiv3[strokavistrela - 1][stolbvistrela] == 5) {
                                    massiv3[strokavistrela - 1][stolbvistrela] =7; massiv4[strokavistrela - 1][stolbvistrela] =7;}

                            }
                        }
                        //33333333333333
                        if ((strokavistrela==5)&&(stolbvistrela==5)) { //ЕСЛИ ЭЛЕМЕНТ F6
                            if ((massiv3[strokavistrela][stolbvistrela - 1] == 1) || (massiv3[strokavistrela - 1][stolbvistrela] == 1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela][stolbvistrela - 1] == 5) {
                                    massiv3[strokavistrela][stolbvistrela - 1] = 7;massiv4[strokavistrela][stolbvistrela - 1] = 7;}
                                if (massiv3[strokavistrela - 1][stolbvistrela] == 5) {
                                    massiv3[strokavistrela - 1][stolbvistrela] =7;massiv4[strokavistrela - 1][stolbvistrela] =7;}
                            }
                        }
                        //44444444444444444444
                        if ((strokavistrela==0)&&(stolbvistrela==5)) { //ЕСЛИ ЭЛЕМЕНТ F6
                            if ((massiv3[strokavistrela+1][stolbvistrela] == 1) || (massiv3[strokavistrela][stolbvistrela-1] == 1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela+1][stolbvistrela] == 5){
                                    massiv3[strokavistrela+1][stolbvistrela + 1] = 7; massiv4[strokavistrela+1][stolbvistrela + 1] = 7;}
                                if (massiv3[strokavistrela][stolbvistrela-1] == 5) {
                                    massiv3[strokavistrela][stolbvistrela-1] =7;massiv4[strokavistrela][stolbvistrela-1] =7;}
                            }
                        }
                        //55555555555555
                        if ((strokavistrela==0)&&(stolbvistrela!=0)&&(stolbvistrela!=5)) { //ЕСЛИ ЭЛЕМЕНТ из первой строки и он не по краям
                            if ((massiv3[strokavistrela][stolbvistrela-1] == 1)||(massiv3[strokavistrela+1][stolbvistrela]==1)||(massiv3[strokavistrela][stolbvistrela+1]==1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela][stolbvistrela - 1] == 5){
                                    massiv3[strokavistrela][stolbvistrela - 1] = 7; massiv4[strokavistrela][stolbvistrela - 1] = 7;}
                                if (massiv3[strokavistrela + 1][stolbvistrela] == 5){
                                    massiv3[strokavistrela + 1][stolbvistrela] =7; massiv4[strokavistrela + 1][stolbvistrela] =7;}
                                if (massiv3[strokavistrela][stolbvistrela + 1] == 5){
                                    massiv3[strokavistrela][stolbvistrela + 1] = 7; massiv4[strokavistrela][stolbvistrela + 1] = 7;}
                            }
                        }
                        //66666666666666666
                        if ((strokavistrela==5)&&(stolbvistrela!=0)&&(stolbvistrela!=5)) { //ЕСЛИ ЭЛЕМЕНТ из последней строки и он не по краям
                            if ((massiv3[strokavistrela][stolbvistrela-1] == 1)||(massiv3[strokavistrela-1][stolbvistrela]==1)||(massiv3[strokavistrela][stolbvistrela+1]==1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela][stolbvistrela - 1] == 5){
                                    massiv3[strokavistrela][stolbvistrela - 1] = 7; massiv4[strokavistrela][stolbvistrela - 1] = 7;}
                                if (massiv3[strokavistrela - 1][stolbvistrela] == 5){
                                    massiv3[strokavistrela - 1][stolbvistrela] =7; massiv4[strokavistrela - 1][stolbvistrela] =7;}
                                if (massiv3[strokavistrela][stolbvistrela + 1] == 5){
                                    massiv3[strokavistrela][stolbvistrela + 1] = 7; massiv4[strokavistrela][stolbvistrela + 1] = 7;}
                            }
                        }
                        //777777777777777777777777
                        if ((stolbvistrela==0)&&(strokavistrela!=0)&&(strokavistrela!=5)) { //ЕСЛИ ЭЛЕМЕНТ из первого столбца и он не по краям
                            if ((massiv3[strokavistrela-1][stolbvistrela] == 1)||(massiv3[strokavistrela][stolbvistrela+1]==1)||(massiv3[strokavistrela+1][stolbvistrela]==1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela-1][stolbvistrela] == 5){
                                    massiv3[strokavistrela-1][stolbvistrela] = 7; massiv4[strokavistrela-1][stolbvistrela] = 7;}
                                if (massiv3[strokavistrela][stolbvistrela+1] == 5){
                                    massiv3[strokavistrela][stolbvistrela+1] =7; massiv4[strokavistrela][stolbvistrela+1] =7;}
                                if (massiv3[strokavistrela+1][stolbvistrela] == 5){
                                    massiv3[strokavistrela+1][stolbvistrela] = 7; massiv4[strokavistrela+1][stolbvistrela] = 7;}
                            }
                        }
                        //88888888888888888888888
                        if ((stolbvistrela==5)&&(strokavistrela!=0)&&(strokavistrela!=5)) { //ЕСЛИ ЭЛЕМЕНТ из последнего столбца и он не по краям
                            if ((massiv3[strokavistrela-1][stolbvistrela] == 1)||(massiv3[strokavistrela][stolbvistrela-1]==1)||(massiv3[strokavistrela+1][stolbvistrela]==1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela-1][stolbvistrela] == 5){
                                    massiv3[strokavistrela-1][stolbvistrela] = 7; massiv4[strokavistrela-1][stolbvistrela] = 7;}
                                if (massiv3[strokavistrela][stolbvistrela-1] == 5) {
                                    massiv3[strokavistrela][stolbvistrela-1] =7;massiv4[strokavistrela][stolbvistrela-1] =7;}
                                if (massiv3[strokavistrela+1][stolbvistrela] == 5) {
                                    massiv3[strokavistrela+1][stolbvistrela] = 7;massiv4[strokavistrela+1][stolbvistrela] = 7;}
                            }
                        }
                        //9999999999999999999
                        if ((stolbvistrela!=0)&&(stolbvistrela!=5)&&(strokavistrela!=0)&&(strokavistrela!=5)) { //ЕСЛИ ЭЛЕМЕНТ  не по краям
                            if ((massiv3[strokavistrela-1][stolbvistrela] == 1)||(massiv3[strokavistrela][stolbvistrela+1]==1)
                                    ||(massiv3[strokavistrela+1][stolbvistrela]==1)||(massiv3[strokavistrela][stolbvistrela-1]==1)) {
                                System.out.print("Ранил! ");
                                massiv4[strokavistrela][stolbvistrela] = 5;
                                massiv3[strokavistrela][stolbvistrela] = 5;
                            }
                            else {
                                System.out.print("Убил! ");
                                massiv4[strokavistrela][stolbvistrela] = 7;
                                massiv3[strokavistrela][stolbvistrela] = 7;
                                if (massiv3[strokavistrela-1][stolbvistrela] == 5){
                                    massiv3[strokavistrela-1][stolbvistrela] = 7; massiv4[strokavistrela-1][stolbvistrela] = 7;}
                                if (massiv3[strokavistrela][stolbvistrela+1] == 5){
                                    massiv3[strokavistrela][stolbvistrela+1] =7; massiv4[strokavistrela][stolbvistrela+1] =7;}
                                if (massiv3[strokavistrela+1][stolbvistrela] == 5) {
                                    massiv3[strokavistrela+1][stolbvistrela] = 7;massiv4[strokavistrela+1][stolbvistrela] = 7;}
                                if (massiv3[strokavistrela][stolbvistrela-1] == 5) {
                                    massiv3[strokavistrela][stolbvistrela-1] = 7;massiv4[strokavistrela][stolbvistrela-1] = 7;}
                            }
                        }

                            int countship = 0;
                        for (int i = 0; i < 6; i++) {
                            for (int k = 0; k < 6; k++) {
                                if (massiv3[i][k] == 1) countship++; //проверка остались ли еще корабли
                            }
                        }
                        if (countship == 0) {fire = false;
                            System.out.println("");
                            System.out.println("!!!!! Поздравляем! Игрок" + igrok + " победил !!!!!");
                            System.out.println();
                            OutputField(massiv5, massiv4);}//вывод поля стреляющего и поля выстрелов стреляющего
                        else { System.out.println("Стреляй еще раз");
                            proverkanavvod=true;}

                    }

                    else {
                        System.out.println("Промазал");
                        System.out.println("Переход к следующему игроку");
                        try {
                            Thread.sleep(2000); //задержка времени при переходе хода от одного игрока к другому
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        massiv4[strokavistrela][stolbvistrela] = 9; //для поля выстрела записываем промах
                        fire = false;
                        for (int i1 = 0; i1 < 10; i1++) {
                            System.out.println();
                        }

                    }
                } else {
                    System.out.println("Некорректный ввод.Ввод должен состоять только из 2 символов.");
                    System.out.println("Первым должен быть символ из ряда:A B C D E F, вторым из ряда: 1 2 3 4 5 6");
                    System.out.println("Введите заново ");
                    proverkanavvod = true;}

            } //цикл проверка на ввод
        }     //цикл выстрелов


    }





}// КОНЕЦ MAIN



//}

