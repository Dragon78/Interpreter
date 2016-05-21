import java.util.Map;


public class MyVisitor extends OptiMathBaseVisitor<String> {
	Map<String, String> memory;
	
	@Override
	public String visitEquation(OptiMathParser.EquationContext ctx) {
		String result;
		visitChildren(ctx);
		//System.out.println(ctx.varmathexp(0).getText());
		//System.out.println(ctx.varmathexp(1).getText());
		//System.out.println("=");
		if (ctx.QUAD() != null){
			System.out.println ("is quadratic");	}
		
		result = visitVarmathexp(ctx.varmathexp(1)).toString();
		System.out.println(result);
		memory.put(ctx.varmathexp(0).getText(), result);
		return result;
	}
	
	@Override
	public String visitVarmathexp(OptiMathParser.VarmathexpContext ctx) {
		String result;
		visitChildren(ctx);
		//System.out.println(ctx.term());
		//System.out.println(ctx.varmathexp());
		System.out.println("child ->"+ctx.getChild(1)); // SIGN
		
		
		if (ctx.getChildCount()==1){
			//result = ctx.term().getText();
			result = visitTerm(ctx.term()).toString();
		} else{
			//String op = ctx.getChild(1).getText();
			if((ctx.getChild(1).getText()).equals("+")){
				String S1 = visitVarmathexp(ctx.varmathexp()).toString();
				String S2 = visitTerm(ctx.term()).toString();
				double val1 = (Double)Double.parseDouble((S1));
				double val2 = (Double)Double.parseDouble((S2));
				result= (new Double(val1+ val2)).toString();
		} else {
			String S1 = visitVarmathexp(ctx.varmathexp()).toString();
			String S2 = visitTerm(ctx.term()).toString();
			double val1 = (Double)Double.parseDouble((S1));
			double val2 = (Double)Double.parseDouble((S2));
			result= (new Double(val2- val1)).toString();
		}
		}
		System.out.println(result);
		return result;
	}
	
	@Override
	public String visitTerm(OptiMathParser.TermContext ctx) {
		String result;
		visitChildren(ctx);
		//System.out.println(ctx.factor());
		//System.out.println(ctx.term());
		//System.out.println(ctx.getChild(1)); // SIGN
		
		if (ctx.getChildCount()==1){
			//result = ctx.factor().getText();
			result = visitFactor(ctx.factor()).toString();
		} else{
			if((ctx.getChild(1).getText()).equals("*")){
				String S1 = visitFactor(ctx.factor()).toString();
				String S2 = visitTerm(ctx.term()).toString();
				double val1 = (Double)Double.parseDouble((S1));
				double val2 = (Double)Double.parseDouble((S2));
				result= (new Double(val1* val2)).toString();
		} else if ((ctx.getChild(1).getText()).equals("^")){
			String S1 = visitFactor(ctx.factor()).toString();
			String S2 = visitTerm(ctx.term()).toString();
			double val1 = (Double)Double.parseDouble((S1));
			double val2 = (Double)Double.parseDouble((S2));
			result= (new Double(Math.pow(val1,val2))).toString();
		}
			else {
			String S1 = visitFactor(ctx.factor()).toString();
			String S2 = visitTerm(ctx.term()).toString();
			double val1 = (Double)Double.parseDouble((S1));
			double val2 = (Double)Double.parseDouble((S2));
			result= (new Double(val1/ val2)).toString();
		}
		}
		System.out.println(result);
		return result;
	}
	
	@Override
	public String visitFactor(OptiMathParser.FactorContext ctx) {
		String result = "1";
		visitChildren(ctx);
		//System.out.println(ctx.trigexp());
		//System.out.println(ctx.logexp());
		//System.out.println(ctx.val()); // SIGN
		//System.out.println(ctx.LPAREN());
		//System.out.println(ctx.RPAREN());
		
		if (ctx.getChildCount()==1){
			result = visitVal(ctx.val());
			//result = ctx.val().getText();
		}else{
			if (ctx.trigexp().getText()!=null){
				String S1 = visitTrigexp(ctx.trigexp()).toString();
				String S2 =visitVal(ctx.val()).toString();
				//String S2 = ctx.val().getText();
				//double val1 = (Double)Double.parseDouble((S1));
				double val1 = (Double)Double.parseDouble((S2));
				double val2=calculateTrig(S1,val1);
				result = new Double(val2).toString();
				
			} else if(ctx.logexp().getText()!=null) {
				String S1 = visitLogexp(ctx.logexp()).toString();
				String S2 =visitVal(ctx.val()).toString();
				if (S1.equals("e")){
					double val2 = (Double)Double.parseDouble((S2));
					result = new Double(Math.log(val2)).toString();
				}else{
				double val1 = (Double)Double.parseDouble((S1));
				double val2 = (Double)Double.parseDouble((S2));
				result = new Double(calculateLog(val1,val2)).toString();
				}
			}
		}
		return result;
	}
	
	@Override
	public String visitVal(OptiMathParser.ValContext ctx) {
		String result = null ;
		visitChildren(ctx);
		//System.out.println(ctx.ID());
		//System.out.println(ctx.NUM());
		//System.out.println(ctx.cons()); // SIGN
		//System.out.println(ctx.varmathexp()); // SIGN
		//System.out.println(ctx.LPAREN());
		//System.out.println(ctx.RPAREN());
		
		if (ctx.NUM() !=null){
			String S1 = ctx.NUM().getText();
			//System.out.print("S1:"+S1);
			result= S1;
		} else if (ctx.getChild(0).toString().equals("(")&& ctx.getChild(2).toString().equals(")")) { 
			String S1 =visitVarmathexp(ctx.varmathexp()).toString();
			System.out.print(S1);
			result = S1;
		}else {
			result = "1";
		}
		
		return result;
	}
	
	@Override
	public String visitTrigexp(OptiMathParser.TrigexpContext ctx) {
		visitChildren(ctx);
		//System.out.println(ctx.SIN());
		//System.out.println(ctx.SEC());
		//System.out.println(ctx.COS());
		//System.out.println(ctx.CSEC());
		//System.out.println(ctx.TAN());
		//System.out.println(ctx.COT());
		//System.out.println(ctx.ARCTAN());
		//System.out.println(ctx.ARCCOS());
		//System.out.println(ctx.ARCSIN());
		//System.out.println(ctx.ARCSEC());
		//System.out.println(ctx.ARCSEC());
		System.out.println("Trig" + ctx.getChild(0).getText());
		return ctx.getChild(0).getText();
		
	}
	
	@Override
	public String visitLogexp(OptiMathParser.LogexpContext ctx) {
		String result;
		visitChildren(ctx);
		//System.out.println(ctx.LOG());
		//System.out.println(ctx.LN());
		//System.out.println(ctx.LPAREN());
		//System.out.println(ctx.val());
		if (ctx.LOG().getText() != null){
			result = visitVal(ctx.val()).toString();
		} else result = "e";
		return result;
	}
	
	@Override
	public String visitCons(OptiMathParser.ConsContext ctx) {
		visitChildren(ctx);
		System.out.println(ctx.Pi());
		System.out.println(ctx.Euler());
		return null;
	}
	
	
	// HELPER METHODS
	
	private double calculateTrig(String exp, double value){
		if(exp.equals("sin")){
			return Math.sin(value);
		}
		if(exp.equals("cos")){
			return Math.cos(value);
		}
		if(exp.equals("tan")){
			return Math.tan(value);
		}
		if(exp.equals("arcsin")){
			return Math.asin(value);
		}
		if(exp.equals("arccos")){
			return Math.acos(value);
		}
		if(exp.equals("arctan")){
			return Math.atan(value);
		}
		if(exp.equals("sec")){
			return 1/Math.cos(value);
		}
		if(exp.equals("csec")){
			return 1/ Math.sin(value);
		}
		if(exp.equals("cot")){
			return 1/ Math.tan(value);
		}
		if(exp.equals("arcsec")){
			return 1/ Math.acos(value);
		}
		if(exp.equals("arccsec")){
			return 1/ Math.asin(value);
		}
		if(exp.equals("arcot")){
			return 1/ Math.atan(value);
		}
		else {
			return value;
		}
	}
	private double calculateLog(double base, double value){
		return Math.log10(value)/Math.log10(base); // Using change of base Formula
	}
}
