package com.example.book.hello;

class Person{

    String name;
    int age;
    static String nation = "CHN";

    public Person(){

    }

    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }

    //静态代码块
    static{
        //执行语句
        int i = 10;
        String name="libai";
        System.out.println(name);
        //不能调用非静态的结构
//		name = "Tom";
//		eat();
        //调用静态结构
        System.out.println(nation);
        showNation();
    }

    static{
        System.out.println("我是静态代码块3");
    }
    static{
        System.out.println("我是静态代码块2");
    }


    //非静态代码块
    {
        System.out.println("我是非静态代码块3");
    }
    {
        //执行语句
        int i = 10;
        System.out.println(i + "：非静态");
        //可以调用静态结构和非静态结构
        name = "Tom";
        nation="zg";
        eat();
        showNation();
    }

    {
        System.out.println("我是非静态代码块2");
    }



    public void eat(){
        System.out.println("人吃饭");
    }
    //随着类的加载而加载
    public static void showNation(){
        System.out.println("我是一个中国人");
    }


}
