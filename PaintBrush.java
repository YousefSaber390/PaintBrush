import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
public class PaintBrush extends Applet implements ItemListener {
	
    //ARRAYT LIST TO SAVE SHIPS IN IT 
	private List<Shape> shapes = new ArrayList<>();
	public void itemStateChanged(ItemEvent e) {} ;

	
	//MOUSE BUTTON 
	private int Xinitial, Yinitial, Xfinal, Yfinal;
    private Button RectangleB1 , OvalB2 , LineB3 , FreeHandB4 , ClearB5 , EraserB6 ;
	private ColorButton  blueB7, redB8, greenB9 ;
	private Checkbox fillCheckbox; 
	
	//VARIABLE FOR CLASS THAT WE CAN ACCESS FROM ANYWHERE INSIDE CLASS
	private Color currentColor;
    private int currentShapeDrawing;
	private boolean drawing;
	private boolean drawEnabled;
    private boolean filling;
    
	//ABSTRACT CLASS WHICH WE CAN INHERITE FROM IT 
	abstract class Shape {
		protected Color color;
        protected boolean filling;
        int x1, y1, x2, y2;
	 public Shape(int x1, int y1, int x2, int y2, Color color, boolean filling){
		    this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
            this.filling = filling;
	 }
        abstract void draw(Graphics g);
    }
// WE CREATE CLASSES THAT WE EXTENDS FROM SHAPE WHICH CONTENT :
//CONSTRACTOR
//DRAW METHOD
    public class Rectangle extends Shape {
        
        public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean filling) {
			super(x1,y1,x2,y2,color,filling);
       
        }
        void draw(Graphics g) {
			g.setColor(color);
            if (filling) {
                g.fillRect(x1, y1, x2, y2);
            } else {
                g.drawRect(x1, y1, x2, y2);
            }
        }
    }

    public class Oval extends Shape {
        
        public Oval(int x1, int y1, int x2, int y2, Color color, boolean filling) {
			super(x1,y1,x2,y2,color,filling);
        }

        void draw(Graphics g) {
			g.setColor(color);
            if (filling) {
                g.fillOval(x1, y1, x2, y2);
            } else {
                g.drawOval(x1, y1, x2, y2);
            }
        }
    }

    public class Line extends Shape {
        private Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
			super(x1,y1,x2,y2,color,false);
        }

        void draw(Graphics g) {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public class FreehandLine extends Shape {
        private Color color;

        public FreehandLine(int x1, int y1, int x2, int y2, Color color) {
          	super(x1,y1,x2,y2,color,false);

        }

        void draw(Graphics g) {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
    }
	
	 public class Eraser extends Shape {
        public Eraser(int x1, int y1, int x2, int y2) {
        	super(x1,y2,x2,y2,Color.white,false);

        }

        void draw(Graphics g) {
            g.setColor(Color.white);
	        g.fillRect(x1, y1, 30, 30);
        }
    }
	
    //I USED INIT BECAUSE WHICH CONTENT WHAT I WANT TO BE USED FOR ONE TIME WHEN THE APP START 
	//I make label and functionalite to buttons 
	// i used anymnous class method 
	public void init() {
		
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());

        RectangleB1 = new Button("Rectangle");
        RectangleB1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
            drawEnabled = true;
            currentShapeDrawing = 0;
            repaint();
		}
        });
        add(RectangleB1);

        OvalB2 = new Button("Oval");
        OvalB2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
            drawEnabled = true;
            currentShapeDrawing = 1;
            repaint();
        }
		});
        add(OvalB2);

        LineB3 = new Button("Line");
        LineB3.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ev) {
            drawEnabled = true;
            currentShapeDrawing = 2;
            repaint();
		}
		});
        add(LineB3);
        
        FreeHandB4 = new Button("Free Hand");
        FreeHandB4.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ev) {
            drawEnabled = true;
            currentShapeDrawing = 3;
            repaint();
		}
		});
        add(FreeHandB4);

        ClearB5 = new Button("Clear");
        ClearB5.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ev) {
            shapes.clear();
            repaint();
        }
		});
        add(ClearB5);
		

        EraserB6 = new Button("Eraser");
        EraserB6.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent ev) {
            drawEnabled = true;
           currentShapeDrawing = 4;
          repaint();
	   }
	   });
       add(EraserB6);
		
		
		blueB7 = new ColorButton("", Color.blue, new ColorListener(Color.blue));
        add(blueB7);

        redB8 = new ColorButton("", Color.red, new ColorListener(Color.red));
        add(redB8);

        greenB9 = new ColorButton("", Color.green, new ColorListener(Color.green));
        add(greenB9);
		
        fillCheckbox = new Checkbox("Fill shape", false);
        fillCheckbox.addItemListener(new ItemListener(){
        public void itemStateChanged(ItemEvent e) {
            filling = fillCheckbox.getState();
            repaint();
        }
		});
        add(fillCheckbox);
    }

    public void paint(Graphics g) {
		
        for (Shape shape : shapes) {
            shape.draw(g);
        }

        // Draw the shape I draw this moment
        if (drawing) {
            if (currentShapeDrawing == 0) {
				if(filling){
                g.setColor(currentColor);
                g.fillRect(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial));
                }
				else {		
		        g.setColor(currentColor);
                g.drawRect(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial));
            }} else if (currentShapeDrawing == 1) {
               if(filling){
                g.setColor(currentColor);
                g.fillOval(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial));
                }
				else {		
		        g.setColor(currentColor);
                g.drawOval(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial));
            }} else if (currentShapeDrawing == 2) {
                g.setColor(currentColor);
                g.drawLine(Xinitial, Yinitial, Xfinal, Yfinal);
            }
        }
    }

	//button details	
    private void setButtonSize(Button button) {
        button.setPreferredSize(new Dimension(250, 150));
        button.setFont(new Font("Arial", Font.BOLD, 30));
    }

	//buttoncolor details		
    class ColorButton extends Button {
        private Color color;

        public ColorButton(String label, Color color, ActionListener actionListener) {
            super("");
            this.color = color;
            addActionListener(actionListener);
        }

        @Override
       public void paint(Graphics g) {
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
	
	//	class MouseListener details 
	//  mousePressed
	//  mouseDragged
	//  mouseReleased
    private class MyMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (drawEnabled) {
                Xinitial = e.getX();
                Yinitial = e.getY();
                drawing = true;
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (drawEnabled && drawing) {
                Xfinal = e.getX();
                Yfinal = e.getY();
                if (currentShapeDrawing == 3) {
                   shapes.add(new FreehandLine(Xinitial, Yinitial, Xfinal, Yfinal, getCurrentColor()));
                    Xinitial = Xfinal;
                   Yinitial = Yfinal;
               }else if (currentShapeDrawing == 4) {
                    shapes.add(new Eraser(Xinitial, Yinitial, Xfinal, Yfinal));
                    Xinitial = Xfinal;
                    Yinitial = Yfinal;
                }
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (drawEnabled && drawing) {
                Xfinal = e.getX();
                Yfinal = e.getY();
                drawing = false;

                Shape newShape = null;

            if (currentShapeDrawing == 0) {
                newShape = new Rectangle(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial), getCurrentColor(), getfilling());
            } else if (currentShapeDrawing == 1) {
                newShape = new Oval(Math.min(Xinitial, Xfinal), Math.min(Yinitial, Yfinal),
                        Math.abs(Xfinal - Xinitial), Math.abs(Yfinal - Yinitial), getCurrentColor(), getfilling());
            } else if (currentShapeDrawing == 2) {
                newShape = new Line(Xinitial, Yinitial, Xfinal, Yfinal, getCurrentColor());
            } else if (currentShapeDrawing == 3) {
                newShape = new FreehandLine(Xinitial, Yinitial, Xfinal, Yfinal, getCurrentColor());
            } else if (currentShapeDrawing == 4) {
                newShape = new Eraser(Xinitial, Yinitial, Xfinal, Yfinal);
            }
			shapes.add(newShape);
           
        }
    }
}
	
	//ColorListener class details
    class ColorListener implements ActionListener {
        private Color color;

        public ColorListener(Color color) {
            this.color = color;
        }

        public void actionPerformed(ActionEvent ev) {
            setCurrentColor(color);
        }
    }
	
    private void setCurrentColor(Color color) {
        currentColor = color;
    }

    private Color getCurrentColor() {
        return currentColor;
    }

    private boolean getfilling() {
        return filling;
    } 
}