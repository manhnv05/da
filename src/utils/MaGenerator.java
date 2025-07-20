/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import repositories.products.SanPhamChiTietRepository;
//import repositories.products.SanPhamRepository;

/**
 *
 * @author Mtt
 */
public class MaGenerator {

//    private SanPhamRepository spRepo = new SanPhamRepository();
//    private SanPhamChiTietRepository spctRepo;


    public static String generate(String prefix) {
        DecimalFormat numFormat = new DecimalFormat("0000");

        Random randInt = new Random();

//        List<SanPham> list = new ArrayList<>();
        
//        list = 
//        int i = list.size();

//        return prefix + numFormat.format(i);
        return prefix + numFormat.format(randInt.nextInt(1, 9999));
    }

    public static String generateByNum(String prefix, int num) {
        DecimalFormat numFormat = new DecimalFormat("0000");

        return prefix + numFormat.format(num);

    }

    public static void main(String[] args) {
        System.out.println(MaGenerator.generate("SP"));
        System.out.println(MaGenerator.generate("SPCT"));
        System.out.println(MaGenerator.generate("HD"));
        System.out.println(MaGenerator.generate("SP"));
    }
}
