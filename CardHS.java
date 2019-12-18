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
	
	//key for 卡牌名称， value for 该名称的卡牌数量
	Map<String,Integer> listWhite = new HashMap<String,Integer>();	
	Map<String,Integer> listBlue = new HashMap<String,Integer>();	
	Map<String,Integer> listPurple = new HashMap<String,Integer>();
	//橙卡不重复机制，so use set
	HashSet<String> setOrange = new HashSet<String>();
	
	//所有卡牌数量，即包括多余卡牌
	int numWhite;
	int numBlue;
	int numPurple;
	int numOrange;
	
	//不包括多余卡牌的数量
	int colWhite;
	int colBlue;
	int colPurple;
	int colOrange;
	
	public static void main(String[] args) {
		
		//开多少包呢？
		int packNum = 70;
		
		CardHS cardHS = new CardHS();
		cardHS.openAllPack(packNum);
		
	}

	void openAllPack(int packNum) {
		//开包，开完第9包还不出橙，设置标志
		for(int i=0;i<packNum;i++) {
			openOnePack();
			if(i==8&&setOrange.isEmpty()) {
				oneOrange9=1;
			}
		}

		//开包结果统计，包括多余卡牌和非多余卡牌
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
		double rand;//随机数决定卡牌成色
		int oneBlue = 0;//一包至少一张蓝卡及以上
		for(int i=0;i<5;i++) {
			
			rand=Math.random();
			
			if(rand<pOrange||(oneOrange9==1&&i==4&&oneOrange==1)) {	//开到第10包最后一张卡，若还不是橙卡，强制出橙；但凡出橙，取消保底橙机制；			
				oneBlue = 1;
				oneOrange =0;
				while(!(setOrange.add("orange"+(int)(Math.random()*orangeKind))));//随机数决定卡牌kind，即具体哪张牌；while决定不重复；
			}else if(rand<pOrange+pPurple) {//出紫卡
				oneBlue = 1;
				addOneCard("purple",listPurple, purpleKind);
			}else if(rand<pOrange+pPurple+pBlue||(i==4&&oneBlue==0)) {//出蓝卡
				oneBlue = 1;
				addOneCard("blue",listBlue, blueKind);
			}else {//出白卡
				addOneCard("white",listWhite, whiteKind);
			}
		}
	}
	
	void addOneCard(String color, Map<String,Integer > listColor, int colorKind) {
		String c = color+(int)(Math.random()*colorKind);//随机数决定卡牌kind，即具体哪张牌
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
			
			if(card.getValue()>2) {//多于2张，即为多余
				colNum+=2;
			}else {
				colNum+=card.getValue();
			}	
		}
		//判断color，给各颜色卡牌数量赋值
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
