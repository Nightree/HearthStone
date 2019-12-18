package priv.Nightree.test.basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class CardHS {
	
	//Single card probability
	double pBlue=0.25/5;
	double pPurple=0.2/5;
	double pOrange=0.05/5;
	
	//Number of card kinds
	int whiteKind=49;
	int blueKind=37;
	int purpleKind=26;
	int orangeKind=23;
	
	//flag of Guaranteed orange; 1 for valid
	int oneOrange = 1;
	int oneOrange9 = 0;
	
	//key for �������ƣ� value for �����ƵĿ�������
	Map<String,Integer> listWhite = new HashMap<String,Integer>();	
	Map<String,Integer> listBlue = new HashMap<String,Integer>();	
	Map<String,Integer> listPurple = new HashMap<String,Integer>();
	//�ȿ����ظ����ƣ�so use set
	HashSet<String> setOrange = new HashSet<String>();
	
	//���п������������������࿨��
	int numWhite;
	int numBlue;
	int numPurple;
	int numOrange;
	
	//���������࿨�Ƶ�����
	int colWhite;
	int colBlue;
	int colPurple;
	int colOrange;
	
	public static void main(String[] args) {
		
		//�����ٰ��أ�
		int packNum = 70;
		
		CardHS cardHS = new CardHS();
		cardHS.openAllPack(packNum);
		
	}

	void openAllPack(int packNum) {
		//�����������9���������ȣ����ñ�־
		for(int i=0;i<packNum;i++) {
			openOnePack();
			if(i==8&&setOrange.isEmpty()) {
				oneOrange9=1;
			}
		}

		//�������ͳ�ƣ��������࿨�ƺͷǶ��࿨��
		cardResult(listWhite,"white");
		cardResult(listBlue,"blue");
		cardResult(listPurple,"purple");
		for (String card : setOrange) {
			System.out.println(card);
			numOrange++;
			colOrange++;
		}
		
		System.out.println("white card total obtain="+numWhite+", collection num="+colWhite+", collection rate="+(double)colWhite/(whiteKind*2));
		System.out.println("blue card total obtain="+numBlue+", collection num="+colBlue+", collection rate="+(double)colBlue/(blueKind*2));
		System.out.println("purple card total obtain="+numPurple+", collection num="+colPurple+", collection rate="+(double)colPurple/(purpleKind*2));
		System.out.println("orange card total obtain="+numOrange+", collection num="+colOrange+", collection rate="+(double)colOrange/(orangeKind*2));
		int totalCol = colWhite+colBlue+colPurple+colOrange;
		System.out.println("total card collection num="+totalCol+", collection rate="+(double)totalCol/247);
	}
	
	void openOnePack() {
		double rand;//������������Ƴ�ɫ
		int oneBlue = 0;//һ������һ������������
		for(int i=0;i<5;i++) {
			
			rand=Math.random();
			
			if(rand<pOrange||(oneOrange9==1&&i==4&&oneOrange==1)) {	//������10�����һ�ſ����������ǳȿ���ǿ�Ƴ��ȣ��������ȣ�ȡ�����׳Ȼ��ƣ�			
				oneBlue = 1;
				oneOrange =0;
				while(!(setOrange.add("orange"+(int)(Math.random()*orangeKind))));//�������������kind�������������ƣ�while�������ظ���
			}else if(rand<pOrange+pPurple) {//���Ͽ�
				oneBlue = 1;
				addOneCard("purple",listPurple, purpleKind);
			}else if(rand<pOrange+pPurple+pBlue||(i==4&&oneBlue==0)) {//������
				oneBlue = 1;
				addOneCard("blue",listBlue, blueKind);
			}else {//���׿�
				addOneCard("white",listWhite, whiteKind);
			}
		}
	}
	
	void addOneCard(String color, Map<String,Integer > listColor, int colorKind) {
		String c = color+(int)(Math.random()*colorKind);//�������������kind��������������
		Integer n = listColor.get(c);
		if(n==null) {					
			listColor.put(c,1);
		}else {
			listColor.put(c,n+1);
		}
	}
	
	void cardResult(Map<String,Integer> listColor, String color) {
		int n=0;
		int colNum=0;
		for (Entry<String, Integer> card : listColor.entrySet()) {
			System.out.println(card.getKey()+"="+card.getValue());
			n+=card.getValue();
			
			if(card.getValue()>2) {//����2�ţ���Ϊ����
				colNum+=2;
			}else {
				colNum+=card.getValue();
			}	
		}
		//�ж�color��������ɫ����������ֵ
		if(color.equals("white")) {			
			numWhite=n;
			colWhite=colNum;
		}else if(color.equals("blue")){
			numBlue=n;
			colBlue=colNum;
		}else if(color.equals("purple")){
			numPurple=n;
			colPurple=colNum;
		}
	}
	
}
