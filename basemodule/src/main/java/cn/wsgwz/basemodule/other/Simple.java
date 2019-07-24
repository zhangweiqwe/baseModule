package cn.wsgwz.basemodule.other;

public class Simple {

    public void d(){

    }

    public static void main(String[] args){
        new B().say();
    }
    interface A{
        final String str = null;
        Simple b = new Simple();
    }

    static class B implements A{
        public static void d() {
            b.d();
        }

        String str = "";
        private void say(){
            str = "fsfsf";
            System.out.println(str+"<-");
        }
    }
}
