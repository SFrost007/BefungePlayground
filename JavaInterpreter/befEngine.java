import java.util.Vector;

public class befEngine{

    private char dir='e', command;
    private Integer tempInt, tempInt2;
    private int xpos=0,ypos=0,xmax=0,ymax=0,lastOp=0;
    private char[][] prog;
    private boolean running=false, openStack=false, wasSpace=false;
    public String output=new String("");
    public Vector stack = new Vector();
    
    public befEngine(String temp, int x, int y){
        prog = new char[x][y];
        xmax=x-1;ymax=y-1;
        String[] lines = new String[y];
        lines = (temp).split("\n");
        for(int i=0;i<lines.length;i++){
            for(int j=0;j<lines[i].length();j++){
                prog[j][i] = lines[i].charAt(j);
            }
        }
        running=true;
    }
    
    public befEngine(){}
    
    public void run(){
        while(command!='@'){
            step();
        }
        running=false;
    }
    
    public void step(){
        command = prog[xpos][ypos];
        wasSpace=false;
        if(openStack==true && command!='"'){
            stack.add(new Integer((int)command));
        }else{
            compute();
        }
        moveCursor();
        if(wasSpace) step();
    }
    
    public void compute(){
        switch(command){
            case '>':dir='e';break;
            case '<':dir='w';break;
            case 'v':dir='s';break;
            case '^':dir='n';break;
            case '"':if(openStack==true){
                         openStack=false;
                     }else{
                         openStack=true;
                     }break;
            case '_':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     if(tempInt.intValue()==0){
                          dir='e';
                     }else{
                          dir='w';
                     }break;
            case '|':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     if(tempInt.intValue()==0){
                          dir='n';
                     }else{
                          dir='s';
                     }break;
            case ',':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     output += ("" + (char)tempInt.intValue());
                     break;
            case '.':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     output += ("" + tempInt.intValue());
                     break;
            case '@':running=false;break;
            case '0':stack.add(new Integer(0));break;
            case '1':stack.add(new Integer(1));break;
            case '2':stack.add(new Integer(2));break;
            case '3':stack.add(new Integer(3));break;
            case '4':stack.add(new Integer(4));break;
            case '5':stack.add(new Integer(5));break;
            case '6':stack.add(new Integer(6));break;
            case '7':stack.add(new Integer(7));break;
            case '8':stack.add(new Integer(8));break;
            case '9':stack.add(new Integer(9));break;
            case ':':tempInt=(Integer)stack.lastElement();stack.add(tempInt);break;
            case '$':stack.removeElementAt(stack.size()-1);break;
            case ' ':wasSpace=true;break;
            case '+':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     tempInt=new Integer(tempInt.intValue()+((Integer)stack.lastElement()).intValue());
                     stack.removeElementAt(stack.size()-1);
                     stack.add(tempInt);break;
            case '-':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     tempInt=new Integer(((Integer)stack.lastElement()).intValue()-tempInt.intValue());
                     stack.removeElementAt(stack.size()-1);
                     stack.add(tempInt);break;
            case '*':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     tempInt=new Integer(tempInt.intValue()*((Integer)stack.lastElement()).intValue());
                     stack.removeElementAt(stack.size()-1);
                     stack.add(tempInt);break;
            case '/':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     tempInt=new Integer(((Integer)stack.lastElement()).intValue()/tempInt.intValue());
                     stack.removeElementAt(stack.size()-1);
                     stack.add(tempInt);break;
            case '%':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     tempInt=new Integer(((Integer)stack.lastElement()).intValue()%tempInt.intValue());
                     stack.removeElementAt(stack.size()-1);
                     stack.add(tempInt);break;
            case '!':tempInt=(Integer)stack.lastElement();
                     stack.removeElementAt(stack.size()-1);
                     if(tempInt.intValue()==0){
                         tempInt=new Integer(1);
                     }else{
                         tempInt=new Integer(0);
                     }
                     stack.add(tempInt);break;
            case '\\':tempInt=(Integer)stack.lastElement();
                      stack.removeElementAt(stack.size()-1);
                      tempInt2=(Integer)stack.lastElement();
                      stack.removeElementAt(stack.size()-1);
                      stack.add(tempInt);
                      stack.add(tempInt2);break;
        }
    }
    
    public void moveCursor(){
        switch(dir){
            case 'e': if(xpos<xmax){xpos++;}else{xpos=0;} break;
            case 'w': if(xpos>=1){xpos--;}else{xpos=xmax;}break;
            case 's': if(ypos<ymax){ypos++;}else{ypos=0;} break;
            case 'n': if(ypos>=1){ypos--;}else{ypos=ymax;}break;
        }
        if(prog[xpos][ypos]==' ' || (prog[xpos][ypos]==' ' && openStack==false)){
            moveCursor();
        }
    }
    
    public boolean isRunning(){
        return running;
    }
        
    public void stop(){
        running = false;
    }
    
    private boolean stackEmpty(){
        if(stack.size()==0){
            return true;
        }else{
            return false;
        }
    }
}
