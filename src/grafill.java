import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Scanner;

 
public class grafill {
	 public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
		     new getFrame();
         }
		 });
	 }
}

 class TopAnimated { // создание по введенным данным координат вершин
	Double xv[];
	Double yv[];
	Double angle;
	Double x0, y0, r;
	ArrayList<Integer> arrver = new ArrayList<Integer>();
	Double stepangle; //Для анимации
	Double angle1; //Для анимации
	int i; //Для анимации

	public TopAnimated() {
		this.x0 = 550.;
		this.y0 = 300.;
		this.r = 150.; //r - радиус окр. x0 y0 - цетр окр. x1 y1 - коор. текущ. вершины
		this.angle = Math.toRadians(360/getFrame.getFrameKolver()); //угол для распределения вершин на окружности
		this.xv = new Double [getFrame.getFrameKolver()];
		this.yv = new Double [getFrame.getFrameKolver()];
		this.arrver = vergran.mver(getFrame.getFrameKolver());
		this.stepangle = Math.toRadians(5.);
		this.angle1 = Math.toRadians(2.);
		this.i = 0;
		this.xv = fillxv(this.x0,this.r);
		this.yv = fillyv(this.y0,this.r);
	}
	public void animatetop() { //АНИМАЦИЯ "ОЖИДАНИЯ" РАСЧЕТА ВЕРШИНЫ
		if (stepangle*angle1 < angle && i < getFrame.getFrameKolver()) {
			if (stepangle*angle1 > 360) stepangle = 0.;
			else stepangle++;
		}
		else {
			angle = angle + Math.toRadians(360/getFrame.getFrameKolver());
			i++;
			if (i >= getFrame.getFrameKolver())
			MyComponent.timer.stop();
		}
	}
	public void paintTopAnimated(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.black);
		Font font = new Font("Calibri",Font.BOLD, 21);
		g2.setFont(font);
		g2.drawString("Иллюстрация введенного графа", 410, 100);
				if (i < getFrame.getFrameKolver() ) {
//					g2.draw(new Ellipse2D.Double(xan-4, yan-4, 8, 8));
					g2.setColor(Color.RED);
					g2.fill(new Ellipse2D.Double(x0 + r*Math.cos(stepangle*angle1)-4, y0 + r*Math.sin(stepangle*angle1)-4, 8, 8));
					g2.fill(new Ellipse2D.Double(x0 + r*Math.cos(-stepangle*(angle1))-4, y0 + r*Math.sin(-stepangle*(angle1))-4, 8, 8));
				} 
				else { 
					for(int j = 0; j < getFrame.getFrameKolver(); j++)  {
					g2.setColor(Color.RED);
					g2.draw(new Ellipse2D.Double(xv[j]-4, yv[j]-4, 8, 8));
					g2.setColor(Color.blue);
					g2.drawString(String.valueOf(arrver.get(j)), (int) (xv[j]-4),(int) (yv[j]-8));
					}
				}
	}
	public void setxv(Double x, int index) {
		xv[index] =  x;
	}
	public Double getxv(int index) {
		return xv[index];
	}
	public void setyv(Double x, int index) {
		yv[index] =  x;
	}
	public Double getyv(int index) {
		return yv[index];
	}
	
	public Double[] fillxv(Double xx0, Double rx) {
		Double[] angle;
		angle = new Double [getFrame.getFrameKolver()+1];
		angle[0] = Math.toRadians(360/getFrame.getFrameKolver()); //угол для распределения вершин на окружности
		for (int i = 0; i<getFrame.getFrameKolver(); i++)
		{
		setxv(x0 + r*Math.cos(angle[i]),i);
		setyv(y0 + r*Math.sin(angle[i]),i);
		angle[i+1] = angle[i] + Math.toRadians(360/getFrame.getFrameKolver());
		}
		return xv;
	}
	public Double[] fillyv(Double yy0, Double ry) {
		Double[] angle;
		angle = new Double [getFrame.getFrameKolver()+1];
		angle[0] = Math.toRadians(360/getFrame.getFrameKolver()); //угол для распределения вершин на окружности
		for (int i = 0; i<getFrame.getFrameKolver(); i++)
		{
		setyv(y0 + r*Math.sin(angle[i]),i);
		angle[i+1] = angle[i] + Math.toRadians(360/getFrame.getFrameKolver());
		}
		return yv;
	}
}

class Gran { // создание по введенным данным координат граней
	Double xnapr[];
	Double ynapr[];
	Double dist;
	Double ko;
	public Gran() {
		TopAnimated coorver = new TopAnimated();
		int t = 0;
		xnapr = new Double [getFrame.intgran.size()];
		ynapr = new Double [getFrame.intgran.size()];
		for (int i=0; i< getFrame.intgran.size()/2; i++) {
             dist = Math.sqrt(Math.pow(coorver.getxv(getFrame.intgran.get(t)-1)-coorver.getxv(getFrame.intgran.get(t+1)-1),2) + Math.pow(coorver.getyv(getFrame.intgran.get(t)-1)-coorver.getyv(getFrame.intgran.get(t+1)-1),2));
             ko = 15/dist; // 15 - расстояние от точки с направлением
             xnapr[i] = coorver.getxv(getFrame.intgran.get(t+1)-1) + (coorver.getxv(getFrame.intgran.get(t)-1)-coorver.getxv(getFrame.intgran.get(t+1)-1))*ko;
             ynapr[i] = coorver.getyv(getFrame.intgran.get(t+1)-1) + (coorver.getyv(getFrame.intgran.get(t)-1)-coorver.getyv(getFrame.intgran.get(t+1)-1))*ko;
             t=t+2;
        }
	}
	public static void paintGran(Graphics g) {
		TopAnimated coordinate = new TopAnimated();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		switch (getFrame.getFrameChoice()) { // рисование граней
		case '1':     // если неориентированный
			int k = 0;
			for (int i=0; i< getFrame.intgran.size()/2; i++) {
	        	 g2.setColor(Color.black);
	             g2.draw(new Line2D.Double(coordinate.getxv(getFrame.intgran.get(k)-1), coordinate.getyv(getFrame.intgran.get(k)-1), coordinate.getxv(getFrame.intgran.get(k+1)-1), coordinate.getyv(getFrame.intgran.get(k+1)-1)));
	             k=k+2;
	           }
			break;
		case '2':   //если ориентированный 
			int t = 0;
			Gran coorgran = new Gran();
			for (int i=0; i< getFrame.intgran.size()/2; i++) {
	        	 g2.setColor(Color.black);
	             g2.draw(new Line2D.Double(coordinate.getxv(getFrame.intgran.get(t)-1), coordinate.getyv(getFrame.intgran.get(t)-1), coordinate.getxv(getFrame.intgran.get(t+1)-1), coordinate.getyv(getFrame.intgran.get(t+1)-1)));
	             g2.setColor(Color.GREEN);
	             g2.fill(new Ellipse2D.Double(coorgran.xnapr[i]-3.5, coorgran.ynapr[i]-3.5, 7, 7)); // направление
	             t=t+2;
	           }
			break;
		default:
			g2.drawString("Ошибка!", 400, 30);
		}
	}
}

class vergran { //класс для заполнения вершин и граней
	 static Scanner in = new Scanner(System.in);
	 
	 //Метод заполнения массива вершин графа
	 public static ArrayList<Integer> mver(int kolver) {
		 ArrayList<Integer> arrver = new ArrayList<Integer>();
		 for (int i=0; i<kolver; i++) arrver.add(i, i+1);
		 return arrver;
	 }
	 //Метод заполнения массива граней графа из массива char с пробелами
	 public static ArrayList<Integer> mgran(char[] chgran) {
		 	ArrayList<Integer> arrgran = new ArrayList<Integer>();
		    String textgran = "";
		    int count1 = 0;
		    for (int i=0; i<chgran.length; i++)
		    {
		    	if (chgran[i] == ' ' && i != 0) 
		    	{
		    		arrgran.add(count1, Integer.parseInt(textgran));
		    		count1++;
		    		textgran = "";
		    	}
		    	else if (((int)chgran[i] >= 48) && ((int)chgran[i] <= 57))// если символ i пирнадлежит символам 0..9
		    	{
		    		textgran = textgran + chgran[i];
		    		if (i == (chgran.length-1)) arrgran.add(count1, Integer.parseInt(textgran));
		    	}
		    }
		    return arrgran;
	 }
}
class MyComponent extends JPanel { //класс для рисования графа
	private static final long serialVersionUID = 1L;
	 static boolean flagpainttop = false;
	 static TopAnimated topanimated;
	 static Timer timer;
	 ActionListener timerListener;
	 public MyComponent() {
		 super();
		 topanimated = new TopAnimated();
		 timerListener = new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 topanimated.animatetop();
				 getFrame.w.repaint();
//				 timer.stop();
			 }
		 };
		 timer = new Timer(1, timerListener);
		 timer.start();
	 }
	 
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
//		arrgran.forEach(System.out::println);
//		System.out.println(mkolver);
//		System.out.println(getChoice());
		topanimated.paintTopAnimated(g);
		if (getFrame.intgran != null)
		Gran.paintGran(g);
	}
}


class getFrame extends JFrame {//класс создания окна

	private static final long serialVersionUID = 1L;
	
  JTextField intypegraf;
  JTextField inkolver;
  JTextField ingran;
  static char fchoice; //
  static int fkolver; //
  static String textgran;
  static ArrayList<Integer> intgran = null;
  static boolean granEntered = false;
  static JPanel w = new JPanel();
  
  public getFrame() {
	  
	  w.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
              BorderFactory.createLineBorder(Color.black)));
	  //setContentPane(w);
	  getContentPane().add(w);
	  setTitle("GrafIllustrator");
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  w.setBackground(Color.getHSBColor(120, 137, 94));
	  w.setLayout(null);
      setSize(800, 600);
      setLocationRelativeTo(null);
      setMinimumSize(new Dimension(800, 600));
      requestFocus(true);
      
      JButton bchoice = new JButton("Ввод"); // кнопка типа графа
      JButton bkolver = new JButton("Ввод"); // кнопка количества вершин
      JButton bgran = new JButton("Ввод");  // кнопка граней
      JButton bmatr = new JButton("Показать"); // кнопка свойств и матриц
      bkolver.setEnabled(false);
      bgran.setEnabled(false);
      bmatr.setEnabled(false);
      
  //Ввод выбора типа графа
      JLabel lablechoice = new JLabel ("<html>Выберите тип графа: 1 - неор.граф<br>2 - ор.граф.<html>");
      lablechoice.setBounds(30, 10, 300, 30);
      w.add(lablechoice);
      //
	  intypegraf = new JTextField();
	  intypegraf.setBounds(30, 45, 50, 20);
	  w.add(intypegraf);
	  //
	  bchoice.setBounds(85, 45, 65, 19);
	  w.add(bchoice);
	  bchoice.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
		 //JOptionPane.showMessageDialog(getFrame.this,intypegraf.getText());
		 if (intypegraf.getText().charAt(0) == '1') {
			 fchoice = intypegraf.getText().charAt(0);
			 JOptionPane.showMessageDialog(getFrame.this,"<html>Вы выбрали НЕОРИЕНТИРОВАННЫЙ граф. <br>Этот выбор нельзя "
		 		+ "изменить до конца работы программы.<html>" );
			 intypegraf.setEditable(false);
			 inkolver.setEditable(true);
			 bkolver.setEnabled(true);
		     inkolver.requestFocus();
		   }
		 else if (intypegraf.getText().charAt(0) == '2') {
			 fchoice = intypegraf.getText().charAt(0);
			 JOptionPane.showMessageDialog(getFrame.this,"<html>Вы выбрали ОРИЕНТИРОВАННЫЙ граф. <br>Этот выбор нельзя "
		 		+ "изменить до конца работы программы.<html>");
			 intypegraf.setEditable(false);
			 inkolver.setEditable(true);
			 bkolver.setEnabled(true);
		     inkolver.requestFocus();
		 } 
		 else JOptionPane.showMessageDialog(getFrame.this,"<html>Ошибка!!! выберите 1 или 2<html>");
		//System.out.println(choice);
	   }
	  });
	  
	intypegraf.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
        	bchoice.doClick();
        }
    }
	});
	  
  //Ввод количества вершин графа
	  JLabel lablekolver = new JLabel ("<html>Введите количество вершин <br>графа:<html>");
      lablekolver.setBounds(30, 125, 300, 30);
      w.add(lablekolver);
	  //
      inkolver = new JTextField();
      inkolver.setBounds(30, 160, 50, 20);
	  w.add(inkolver);
	  inkolver.setEditable(false);
	  //
	  bkolver.setBounds(85, 160, 65, 19);
	  w.add(bkolver);
	  bkolver.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			 if ((Integer.parseInt(inkolver.getText())<=0) || (Integer.parseInt(inkolver.getText())>30))  JOptionPane.showMessageDialog(getFrame.this,"<html>Ошибка!! <html>" );
			 else {
				 fkolver = Integer.parseInt(inkolver.getText());
				 JOptionPane.showMessageDialog(getFrame.this,"<html>Вершины графа будут пронумированы от 1 до " +fkolver+ ".<html>" );
				 ingran.setEditable(true);
				 bgran.setEnabled(true);
			     inkolver.setBackground(Color.getHSBColor(10, 10, 10));
			     ingran.requestFocus();
			     MyComponent comp = new MyComponent();
			     w.setLayout(new BorderLayout());
			     w.add(comp);
			     w.revalidate();
			     w.repaint();
			 }
			 //System.out.println(kolver);
		   }
		  });
	  
	inkolver.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
        	bkolver.doClick();
        }
    }
	});
	  
	//Ввод граней графа 
	  JLabel lablegran = new JLabel ("<html>Введите грани графа через пробел.<br> Пример:"
	  		+ " `1 3 5 8`, где пары<br> 1 и 3, 5 и 8 - смежные вершины. <html>");
	  lablegran.setBounds(30, 240, 400, 45);
      w.add(lablegran);
      //
      ingran = new JTextField();
      ingran.setBounds(30, 290, 150, 20);
	  w.add(ingran);
	  ingran.setEditable(false);
	  //
	  bgran.setBounds(185, 290, 65, 19);
	  w.add(bgran);
	  bgran.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   if (vergran.mgran(ingran.getText().toCharArray()).size() % 2 == 0) {
			intgran = vergran.mgran(ingran.getText().toCharArray());
		    ingran.setBackground(Color.getHSBColor(10, 10, 10));
		    bmatr.setEnabled(true);
			  MyComponent comp = new MyComponent();
		    w.setLayout(new BorderLayout());
		    w.add(comp);
		    w.revalidate();
		    w.repaint();
			   }
			   else {
				   JOptionPane.showMessageDialog(getFrame.this,"<html>Ошибка в вводе граней!!<html>" );
			   }
		    //System.out.println(grafill.count2);
			 //MyComponent.arrgran.forEach(System.out::println);
			 //System.out.println(MyComponent.arrgran.size());
		   }
		  });
	  
	  ingran.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if(e.getKeyCode() == KeyEvent.VK_ENTER){
		        	bgran.doClick();
		        }
		    }
			});
	  
    //Свойства и матрицы
	  JLabel lablematr = new JLabel ("<html>Свойства и матрицы <br>введенного графa.<html>");
	  lablematr.setBounds(70, 480, 300, 45);
	  w.add(lablematr);
	  bmatr.setBounds(80, 522, 90, 19);
	  w.add(bmatr);
	  bmatr.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   new MatrFrame();
		   }
		  });
	  //add(w);
	  //repaint();
	  setVisible(true);
  }

	public static char getFrameChoice() {
		return fchoice;
	}
	public static int getFrameKolver() {
		return fkolver;
	}
	public static ArrayList<Integer> getFrameGran() {
		return intgran;
	}
}

class MatrFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	   static TableSmgModel tSmg = new TableSmgModel();
       static JTable tableSmg = new JTable(tSmg);
 	   static TableIncModel tInc = new TableIncModel();
       static JTable tableInc = new JTable(tInc);
	
public MatrFrame() {
	
	   setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	   setTitle("Свойства и матрицы");
	   setSize(700, 500);
	   setLocationRelativeTo(null);
	   setLayout(new GridBagLayout());
	   
	   SmgMatr(); // создание и вывод матрицы смежности
	   TableIncModel.i=0; // УБРАТЬ позволяет посмотерть повторно таблицу инцидентности
	   TableIncModel.k=3; // УБРАТЬ
	   IncMatr(); // создание и вывод матрицы инцидентности
	   Properties(); // вывод свойств графа
       pack();
       
       setVisible(true);
 }
  void SmgMatr() { // создание и заполнение матрицы смежности
	  
	   JLabel lableSmg = new JLabel ("<html>Матрица смежности:<html>");
	   add(lableSmg, new GridBagConstraints(0,0,1,1,0,0, GridBagConstraints.NORTH,
    		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));
	   
       JScrollPane Smgscrollpane = new JScrollPane(tableSmg);
       Smgscrollpane.setPreferredSize(new Dimension(400, 110));
       add(Smgscrollpane, new GridBagConstraints(0,1,1,1,0,0, GridBagConstraints.NORTH,
    		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));
       
       TableColumn column = null;
       for (int i = 0; i < getFrame.fkolver; i++) { // установка толщины ячеек таблицы
           column = tableSmg.getColumnModel().getColumn(i);
           if (i == 0 && getFrame.fkolver <=5) 
               column.setPreferredWidth(15);
               else
           if (i == 0)
               column.setPreferredWidth(25); // 0-й столбец меньше остальных
           else 
               column.setPreferredWidth(100);
       }
       
  	   for (int i = 0; i < getFrame.fkolver; i++) // заполенение первого столбца с названиями вершин 
  	   tableSmg.setValueAt(i+1, i, 0);
  	   
  	   if (!(getFrame.intgran == null)) 
  	  { // заполнение матрицы нулями
            for (int i = 1; i <= getFrame.fkolver; i++) { //столбцы
            	for (int j = 0; j < getFrame.fkolver; j++) //строки
            		tableSmg.setValueAt(0, j, i);
            }
          	switch(getFrame.fchoice) // заполнение единицами 
               {
                  case '1':
                	 for (int i = 0; i < getFrame.intgran.size() ; i=i+2) 
            	 {
            		 tableSmg.setValueAt(1, getFrame.intgran.get(i+1)-1, getFrame.intgran.get(i));
            		 tableSmg.setValueAt(1, getFrame.intgran.get(i)-1, getFrame.intgran.get(i+1));
            	 }
            	 break;
                  case '2': 
                	  for (int i = 0; i < getFrame.intgran.size() ; i=i+2) 
                 	 {
                		  tableSmg.setValueAt(1, getFrame.intgran.get(i+1)-1, getFrame.intgran.get(i));
                 	 }
                 	 break;
               }
  	  }
 }
  
  void IncMatr() { // создание и заполнение матрицы инцидентности
	  JLabel lableInc = new JLabel ("<html>Матрица инцидентности:<html>");
	   add(lableInc, new GridBagConstraints(0,2,1,1,0,0, GridBagConstraints.NORTH,
   		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));

      JScrollPane Incscrollpane = new JScrollPane(tableInc);
      Incscrollpane.setPreferredSize(new Dimension(400, 110));
      add(Incscrollpane, new GridBagConstraints(0,3,1,1,0,0, GridBagConstraints.NORTH,
   		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));
      TableColumn column = null;
      
      for (int i = 0; i <getFrame.intgran.size()/2; i++) { // установка толщины ячеек таблицы
          column = tableInc.getColumnModel().getColumn(i);
          if (i == 0 && getFrame.intgran.size()/2 <=5) 
              column.setPreferredWidth(10);
              else
          if (i == 0)
              column.setPreferredWidth(25); // 0-й столбец меньше остальных
          else 
              column.setPreferredWidth(100);
      }
      
 	   for (int i = 0; i < getFrame.fkolver; i++) // заполенение первого столбца с названиями вершин 
 	   tableInc.setValueAt(i+1, i, 0);
 	   
 	   if (!(getFrame.intgran == null)) 
 	  { // заполнение матрицы нулями
           for (int i = 1; i <= getFrame.intgran.size()/2; i++) { //столбцы
           	for (int j = 0; j < getFrame.fkolver; j++) //строки
           		tableInc.setValueAt(0, j, i);
           }
         	switch(getFrame.fchoice) // заполнение необходимыми значениями
              {
                 case '1':
                	 	int v = 1;
                	 	for (int i = 0; i < getFrame.intgran.size() ; i=i+2) {
                		 tableInc.setValueAt(1, getFrame.intgran.get(i)-1, v);
                		 tableInc.setValueAt(1, getFrame.intgran.get(i+1)-1, v);
                		 v++;
                	 	}
                 break;
                 case '2': 
                  int u = 1;
               	  for (int i = 0; i < getFrame.intgran.size() ; i=i+2) 
                	 {
               		tableInc.setValueAt(-1, getFrame.intgran.get(i)-1, u);
               		tableInc.setValueAt(1, getFrame.intgran.get(i+1)-1, u);
               		u++;
                	 }
                 break;
              }
 	  }
  }
  
  void Properties() { // Свойства введенного графа
	  
	   JLabel lableProp = new JLabel ("<html>Свойства графа:<html>");
	   add(lableProp, new GridBagConstraints(0,4,1,1,0,0, GridBagConstraints.NORTH,
  		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));
	   JTextArea PropField = new JTextArea();
	   JScrollPane Propscrollpane = new JScrollPane(PropField);
	   PropField.setEditable(false);
       Propscrollpane.setPreferredSize(new Dimension(70, 90));
       add(Propscrollpane, new GridBagConstraints(0,5,1,1,0,0, GridBagConstraints.NORTH,
    		   GridBagConstraints.BOTH, new Insets(5,5,10,5),0,0));
       
       int count = 0; // нумерация свойств
       
       if (getFrame.fchoice == '1') { //Ориентированный - неориентированный
    	   count++;
    	   PropField.setText(PropField.getText()+count+")"+" Граф неориентированый \r\n");
       }
       else if (getFrame.fchoice == '2') {
    	   count++;
    	   PropField.setText(PropField.getText()+count+")"+" Граф ориентированый \r\n");
       }
       
       // степени вершин графа
       count++;
       PropField.setText(PropField.getText()+count+")"+" Степени вершин (i,j), i - вершина, j - степень:  \r\n");
       int degree = 0;
       int prostcount = 0; //проверка на простой граф
       int[] countdegree1 = new int[getFrame.fkolver]; //для проверки на регулярный граф
       PropField.setText(PropField.getText()+"{");
       for (int i = 1; i <= getFrame.fkolver; i++) {
    	   degree = 0;
    	   PropField.setText(PropField.getText()+"("+i+",");
    	   for (int j = 0; j < getFrame.intgran.size(); j++) {
    		   if (i == getFrame.intgran.get(j)) degree++;
    		   if (j == getFrame.intgran.size()-1 && i != getFrame.fkolver) {
    		   PropField.setText(PropField.getText() + degree + "),");
    		   if (degree % 2 != 0) prostcount++; //проверка на простой граф
    		   countdegree1[i-1] = degree; //для проверки на регулярный граф
    		   }
    		   if (j == getFrame.intgran.size()-1 && i == getFrame.fkolver) {
    		   PropField.setText(PropField.getText()+degree+")}\r\n");
    		   if (degree % 2 != 0) prostcount++; //проверка на простой граф
    		   countdegree1[i-1] = degree; //для проверки на регулярный граф
    		   }
    	   }
       }
       
       //Проверка на простой граф
       if (prostcount % 2 == 0 && prostcount != 0) {
    	   count++;
    	   PropField.setText(PropField.getText()+count+")"+" Простой граф \r\n");
       }
       
       //Проверка на полный граф
       int polncount, polncountres = 0;
       for (int i = 0; i < getFrame.fkolver ; i++) 
       {
    	   polncount = 0;
    	   for (int j = 1; j <= getFrame.fkolver ; j++) 
    	   	{
    		   if ((int)tableSmg.getValueAt(i,j) == 1 && i != j-1) polncount++;
    	   	}
    	   if (polncount == getFrame.fkolver-1) polncountres++;
        }
       if (polncountres == getFrame.fkolver) {
           count++;
     	   PropField.setText(PropField.getText()+count+")"+" Полный граф \r\n");
       }
       
       //Проверка на регулярный граф
       boolean countreg = false;
       for (int i = 0; i < countdegree1.length-1 ; i++) 
       {
    	   if (countdegree1[i] == countdegree1[i+1])
    		   countreg = true;
    	   else { 
    		   countreg = false;
    		   break;
    	   }
       }
       if (countreg == true) {
    	   count++;
     	   PropField.setText(PropField.getText()+count+")"+" Регулярный граф со степенью " + countdegree1[0]+"\r\n");
       }
       
  }
}

class TableSmgModel extends AbstractTableModel{ //Модель таблицы, содержащая матрицу смежности
	private static final long serialVersionUID = 1L;
	int ColumnCount = getFrame.fkolver+1;
	int RowCount = getFrame.fkolver;
	Object[][] dataSmg = new Object[RowCount][ColumnCount];
	
	public void setValueAt(Object value, int row, int col) {
		dataSmg[row][col] = value;
        fireTableCellUpdated(row, col);
    }

	@Override
	public int getRowCount() { //Возвращает количество строк в таблице
		if ((RowCount > 0) && (RowCount <=30))
		return RowCount;
		else
	    return 0;
	}

	@Override
	public int getColumnCount() { //Возвращает количество столбцов в таблице
		if ((ColumnCount > 0) && (ColumnCount <=30))
		return ColumnCount;
		else
	    return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { //Возвращает значение ячейки по строке и столбцу
		return dataSmg[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		for (; columnIndex < ColumnCount;) {
			if (columnIndex == 0) return "";
			else
			return String.valueOf(columnIndex);
		}
		return "";
	}

}

class TableIncModel extends AbstractTableModel{ //Модель таблицы, содержащая матрицу инцидентности
	private static final long serialVersionUID = 1L;
	int ColumnCount = getFrame.intgran.size()/2+1;
	int RowCount = getFrame.fkolver;
	Object[][] dataSmg = new Object[RowCount][ColumnCount];
	static int k = 3, i =0;
	
	public void setValueAt(Object value, int row, int col) {
		dataSmg[row][col] = value;
        fireTableCellUpdated(row, col);
    }

	@Override
	public int getRowCount() { //Возвращает количество строк в таблице
		if ((RowCount > 0) && (RowCount <=30))
		return RowCount;
		else
	    return 0;
	}

	@Override
	public int getColumnCount() { //Возвращает количество столбцов в таблице
		return ColumnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { //Возвращает значение ячейки по строке и столбцу
		return dataSmg[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		for (; columnIndex < ColumnCount;) {
			if (columnIndex >= TableIncModel.k) {
				TableIncModel.i=TableIncModel.i+2;
				TableIncModel.k=TableIncModel.k+2;
			} 
			if (columnIndex == 0) return "";
			else if (columnIndex % 2 != 0)
			return String.valueOf(getFrame.intgran.get(columnIndex-1+i))+" "+String.valueOf(getFrame.intgran.get(columnIndex+i));
			else return String.valueOf(getFrame.intgran.get(columnIndex+i))+" "+String.valueOf(getFrame.intgran.get(columnIndex+1+i));
		}
		return "";
	}
//
}