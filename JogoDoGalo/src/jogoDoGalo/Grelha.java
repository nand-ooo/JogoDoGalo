// classe responsavel pela grelha de jogo
package jogoDoGalo;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

public class Grelha {
	
    private ImageIcon figura;
    private Point topo;	

    private ImageIcon jogadaO = new ImageIcon("art/O.png");
    private ImageIcon jogadaX = new ImageIcon("art/X.png");
    
    // Array que guarda as jogadas
    // inicializado com um espaço em todas as posições
	private char jogadas[][] = new char [3][3];
	{
		for( int x= 0; x < 3; x++ )
			for( int y= 0; y < 3; y++ )
				jogadas[x][y] = ' ';
	}
	
	public char[][] getJogadas(){
		return jogadas;
	}

    public Grelha ( ImageIcon fig, Point t){
    	topo = t;
    	figura = fig;
    }    
    
    // método que conforme o char no array de jogadas desenha a imagem correta na grelha
    public void desenhar( Graphics g ) { 
    	figura.paintIcon( null, g, topo.x, topo.y );       	
    	    	
    	// desenhar jogadas
    	for( int x = 0; x < 3; x++ )
			for( int y = 0; y < 3; y++ ) {
				if(jogadas[x][y] == 'X')
					jogadaX.paintIcon(null, g, 20 + 100 * y, 20 + 100 * x);
				else if (jogadas[x][y] == 'O')
					jogadaO.paintIcon(null, g, 20 + 100 * y, 20 + 100 * x);
			}				
    }    
    
    public void limpaJogo() {
    	for( int x= 0; x < 3; x++ )
			for( int y= 0; y < 3; y++ )
				jogadas[x][y] = ' ';
    }
}
