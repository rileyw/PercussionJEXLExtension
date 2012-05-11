package com.percussion.forum;

import com.percussion.extension.IPSJexlExpression;
import com.percussion.extension.IPSJexlMethod;
import com.percussion.extension.IPSJexlParam;
import com.percussion.extension.PSJexlUtilBase;

/**
 * Demonstration of creating a class that extends PSJexlUtilBase
 * and implements IPSJexlExpression.
 * 
 * @author rileyw
 * @see com.percussion.extension.IPSJexlExpression
 * @see com.percussion.extension.IPSJexlMethod
 * @see com.percussion.extension.IPSJexlParam
 * @see com.percussion.extension.PSJexlUtilBase
 */
public class JEXLExample extends PSJexlUtilBase implements IPSJexlExpression {
	public JEXLExample() {}
	
	@IPSJexlMethod(description="Description of the method",
			params={
				@IPSJexlParam(name="Argument 1",description="Description of this parameter"),
				@IPSJexlParam(name="Argument 2",description="Description of this parameter")}
	)
	public String invokeMethod(String arg0, String arg1){
		return "Invoking method with parameters:" + arg0 +" and "+ arg1;
	}
}
