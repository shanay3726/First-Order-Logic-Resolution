import java.util.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
class homework{
	static String [] query;
	static String [] db;
	static int k,n;
	static ArrayList<String> arr = new ArrayList<>();
	static HashMap<String,Integer> noofvariables;
	static HashMap<String,String> sentences;
	static HashMap<String,ArrayList<Integer>> sentencesarray;
	static HashMap<String,String> negation;
	static int hello;
	static long startTime;
	//static long startTime,endTime,startTime1,endTime1;
	public static void main(String [] args)throws IOException
	{
		int i,j,l;
		Scanner sc = new Scanner(System.in);
		File file=new File("input.txt");
		BufferedReader br= new BufferedReader(new FileReader(file));
		String type,st,timing;
		st=br.readLine();
		n=Integer.parseInt(st);
		query=new String[n];
		for(i=0;i<n;i++)
		{
			query[i]=br.readLine();
			query[i]=query[i].replaceAll("\\s","");
			//System.out.println(query[i]);
		}
		st=br.readLine();
		k=Integer.parseInt(st);
		db=new String[k];
		for(i=0;i<k;i++)
		{
			db[i]=br.readLine();
			db[i]=db[i].replaceAll("\\s","");
			//System.out.println(db[i]+" length "+db[i].length());
		}
		gochange();
		for(i=0;i<k;i++)
		{
			//db[i]=br.readLine();
			//db[i]=db[i].replaceAll("\\s","");
			//System.out.println(db[i]+" length "+db[i].length());
		}
		HashMap <String,String> hm = new HashMap<>();
		knowledge();
		distribute();
		Boolean [] answers=new Boolean[n];
		for(i=0;i<n;i++)
		{
			answers[i]=resolution(query[i]);
			System.out.println(answers[i]);
		}
		//System.out.println("ANSWERS ARE");
		for(i=0;i<n;i++)
			System.out.println(answers[i]);
		writefile(answers);

	}
	public static void gochange()
	{
		int i,j,m;
		for(i=0;i<k;i++)
		{
			for(j=0;j<db[i].length();j++)
			{
				if(db[i].charAt(j)=='('||db[i].charAt(j)==',')
				{
					if(db[i].charAt(j+1)>='a'&&db[i].charAt(j+1)<='z')
					{
						m=j+1;
						if(m<db[i].length())
						{
							while(db[i].charAt(m)!=','&&db[i].charAt(m)!=')')
							{
								
								m++;
							}
							db[i]=db[i].substring(0,m)+Integer.toString(i)+db[i].substring(m,db[i].length());
						}
					}
				}
			}
		}
	}
	public static void writefile(Boolean [] answers)
	{
		try{
		FileWriter file1 = new FileWriter("output.txt");
		PrintWriter p1 = new PrintWriter(file1);
		for(int i=0;i<n;i++)
		{
			if(answers[i])
			{
				p1.println("TRUE");
			}
			else
			{
				p1.println("FALSE");
			}
		}
		file1.close();
		System.exit(0);
		}
		catch(IOException e)
		{
			//System.out.println("ERROR");
		}
	}
	public static Boolean resolution(String queryy)
	{
		Boolean answer=false;
		// try
		// {
		String temp1;
		//Boolean answer;
		int len=queryy.length();
		if(queryy.charAt(0)=='~')
		{
			temp1=queryy.substring(1,len);
			queryy=temp1;
		}
		else
		{
			temp1=queryy;
			queryy="~"+temp1;
		}	
		//System.out.println(queryy);
		arr.add(queryy);
		//System.out.println();
		for(int i=0;i<arr.size();i++)
		{	
			//System.out.println(arr.get(i));
		}
		putinhashmap();
		startTime=System.nanoTime();
		answer=resolute(queryy);
		//System.out.println("Answer is "+answer);
		arr.remove(queryy);
		return answer;
		
	}
	public static void putinhashmap()
	{
		int i,j,s,start;
		sentences = new HashMap<>();
		sentencesarray=new HashMap<>();
		noofvariables = new HashMap<>();
		negation = new HashMap<>();
		ArrayList<Integer> sens;
		for(i=0;i<arr.size();i++)
		{	start=0;
			//System.out.println(i);
			for(j=0;j<arr.get(i).length();j++)
			{
				if(arr.get(i).charAt(j)=='(')
				{	
					if(!sentences.containsKey(arr.get(i).substring(start,j)))
					{
						//System.out.println("Hashmap "+arr.get(i).substring(start,j));
						sentences.put(arr.get(i).substring(start,j),"0");
						while(j<arr.get(i).length() && arr.get(i).charAt(j)!='|')
						{
							j++;
						}
						start=j+1;
					}
					else
					{
						while(j<arr.get(i).length() && arr.get(i).charAt(j)!='|')
						{
							j++;
						}
						start=j+1;
					}
				}
			}
		}
		for (String name : sentences.keySet())
		{	sens=new ArrayList<>();
			String indices="";
			int index;
            //System.out.println("key: " + name); 
            for(i=0;i<arr.size();i++)
            {	//String indices="";
            	if(arr.get(i).contains(name))
            	{	
            		index=arr.get(i).indexOf(name);
            		int ss=0;
            		for(j=0;j<arr.get(i).length();j++)
            		{	int index1;
						index1=arr.get(i).indexOf(name,j);
						if(name.charAt(0)!='~' && index1>0 && arr.get(i).charAt(index1-1)!='~')
	            		{
	            			//System.out.println(i);
	            			indices=indices.concat(Integer.toString(i)+",");
	            			sens.add(i);
	            			ss=1;
	            			break;
	            		}
	            		else if(index1==-1)
	            		{
	            			break;
	            		}
	            	}
            		if(name.charAt(0)!='~' && index==0 && ss==0)
            		{
            			indices=indices.concat(Integer.toString(i)+",");
            			sens.add(i);
            		}
            		else if(name.charAt(0)=='~')
            		{
            			indices=indices.concat(Integer.toString(i)+",");
            			sens.add(i);
            		}

            	}
            	
            }
            sentences.put(name,indices);
            sentencesarray.put(name,sens);
            //System.out.println("String = "+indices);
		}

        for (String name : sentences.keySet())
		{
			int count=0,senno,inn;
			String sen,lol;
			sen = sentences.get(name);
			//System.out.println(sen);
			senno = sen.indexOf(",");
			senno = Integer.parseInt(sen.substring(0,senno));
			//System.out.println(senno);
			inn=arr.get(senno).indexOf(name);
			lol=arr.get(senno);
			while(lol.charAt(inn)!=')')
			{
				if(lol.charAt(inn)==',')
				{
					count=count+1;
					inn++;
				}
				else
				{
					inn++;
				}
			}
			count=count+1;
			//System.out.println("Count of "+name+" is "+count);
			noofvariables.put(name,count);

		}
		for (String name : sentences.keySet())
		{	String name1;
			if(name.charAt(0)=='~')
			{
				name1=name.substring(1,name.length());
			}
			else
			{
				name1="~"+name;
			}
			negation.put(name,name1);
		}
		//System.out.println(negation);
		
	}
	public static Boolean resolute(String answer)
	{
		if(answer.equals(""))
		{	//System.out.println("HELL YEAH");
			return true;
		}
		long endTime,totalTime;
		//long totaltime1;
		//long startTime,endTime;
		// else if(timing condition)
		// {
		// 	return false;
		// }
		String a,b,left,right,other;
		int i,j=0;;
		Boolean condition=false;
		ArrayList <Integer> start = new ArrayList<>();
		ArrayList <Integer> end = new ArrayList<>();
		ArrayList <String> predicates = new ArrayList<>();
		other=answer;
		//ArrayList
		for(i=0;i<answer.length();i++)
		{
			if(answer.charAt(i)=='(')
				start.add(i);
			else if(answer.charAt(i)==')')
				end.add(i);
		}
		for(i=0;i<start.size();i++)
		{
			if(i==0)
				predicates.add(answer.substring(0,start.get(i)));
			else
				predicates.add(answer.substring(end.get(i-1)+2,start.get(i)));
			//System.out.println("Predicates "+predicates.get(i));
		}
		//System.out.println(predicates);
		//startTime1=System.nanoTime();
		for(i=0;i<predicates.size();i++)
		{	try{
			ArrayList <Integer> possible = new ArrayList<>();
			//if(negation.containsKey(predicates.get(i)))
				possible=sentencesarray.get(negation.get(predicates.get(i)));
			//else
			//	return false;
			//System.out.println(possible);
			
				for(j=0;j<possible.size();j++)
				{	//System.out.println("In unification");
					try{
						if(unify(answer,arr.get(possible.get(j))))
						{	//System.out.println("after unification");
							hello=j;
							//startTime=System.nanoTime();
							//endTime1=System.nanoTime();
							//totaltime1=(endTime1-startTime1)/1000000000;
							//if(totaltime1>20)
							//	return false;
							endTime=System.nanoTime();
							totalTime=(endTime-startTime)/1000000000;
							if(totalTime>20)
								return false;
							
							other=res(answer,arr.get(possible.get(j)));
							condition=resolute(other);
							if(condition==true)
								return true;
						}
					}
					catch(StackOverflowError t)
					{
						//System.out.println("stack over");
						j++;
						return false;

					}
				}		
			
			// else
			// {
			// 	return false;	
			// }
			}
			catch(NullPointerException n)
			{
				//System.out.println("NULL POINTER");
				return false;
			}
			//changeorder(j);
		}
		//changeorder(j);
		return condition;
	}
	public static void changeorder(int j)
	{
		String o;
		o=arr.get(j);
		//System.out.println("o is "+o );
		arr.remove(j);

		arr.add(o);
	}
	public static Boolean unify(String answer,String rightside)
	{
		ArrayList <Integer> start = new ArrayList<>();
		ArrayList <Integer> end = new ArrayList<>();
		ArrayList <String> predicates = new ArrayList<>();
		ArrayList <Integer> start1 = new ArrayList<>();
		ArrayList <Integer> end1 = new ArrayList<>();
		ArrayList <String> predicates1 = new ArrayList<>();
		int startat,startend1,index1,index2,i,j,m;
		for(i=0;i<answer.length();i++)
		{
			if(answer.charAt(i)=='(')
				start.add(i);
			else if(answer.charAt(i)==')')
				end.add(i);
		}
		for(i=0;i<start.size();i++)
		{
			if(i==0)
				predicates.add(answer.substring(0,start.get(i)));
			else
				predicates.add(answer.substring(end.get(i-1)+2,start.get(i)));
		}
		//System.out.println(rightside);
		for(i=0;i<rightside.length();i++)
		{
			if(rightside.charAt(i)=='(')
				start1.add(i);
			else if(rightside.charAt(i)==')')
				end1.add(i);
		}
		for(i=0;i<start1.size();i++)
		{
			if(i==0)
				predicates1.add(rightside.substring(0,start1.get(i)));
			else
				predicates1.add(rightside.substring(end1.get(i-1)+2,start1.get(i)));
		}
		//System.out.println("IN UNI FUNC");
		//System.out.println(predicates);
		//System.out.println(predicates1);
		for(i=0;i<predicates.size();i++)
		{
			for(j=0;j<predicates1.size();j++)
			{	
				if(predicates.get(i).equals(negation.get(predicates1.get(j))))
				{	//System.out.println(predicates.get(i));
					//System.out.println(predicates1.get(j));
					if(i==0)
					{
						index1=0;
					}
					else
					{
						index1=end.get(i-1)+2;
					}
					if(j==0)
					{
						index2=0;
					}
					else
					{
						index2=end1.get(j-1)+2;
					}
					ArrayList<String> aa = new ArrayList<>();
					ArrayList<String> bb = new ArrayList<>();
					for(m=index1;m<answer.length();m++)
					{
						if(answer.charAt(m)=='('||answer.charAt(m)==',')
						{
							startat=m+1;
							//System.out.println("aa"+startat);
							m++;
							while(answer.charAt(m)!=','&& answer.charAt(m)!=')')
							{
								m++;
							}
							//System.out.println(m);
							aa.add(answer.substring(startat,m));
							//System.out.println("aa"+answer.substring(startat,m));
							m=m-1;
						}
						else if(answer.charAt(m)==')')
						{
							break;
						}
					}
					for(m=index2;m<rightside.length();m++)
					{
						if(rightside.charAt(m)=='('||rightside.charAt(m)==',')
						{
							startat=m+1;
							m++;
							while(rightside.charAt(m)!=','&& rightside.charAt(m)!=')')
							{
								m++;
							}
							bb.add(rightside.substring(startat,m));
							m=m-1;
						}
						else if(rightside.charAt(m)==')')
						{
							break;
						}
					}
					//System.out.println("Arguments are "+aa);
					//System.out.println(bb);
					int count=0;
					//System.out.println(aa.size());
					for(m=0;m<aa.size();m++)
					{	//System.out.println(aa.get(m).charAt(0)+" = "+bb.get(m).charAt(0));
						if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='z')
						{
							//System.out.println("In 1st condition");
							count++;
						}
						else if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='A' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='Z')
						{
							//System.out.println("In 2nd condition");
							count++;
						}
						else if(aa.get(m).charAt(0)>='A' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='Z' && bb.get(m).charAt(0)<='z')
						{
							//System.out.println("In 3rd condition");
							count++;
						}
						else if(aa.get(m).equals(bb.get(m)))
						{
							//System.out.println("In 4th condition");
							count++;
						}
					}
					//System.out.println("Count is "+count);
					if(count==aa.size())
					{	//System.out.println("ITS true");
						return true;
					}
				}
			}
		}
		return false;
	}
	public static String res(String answer,String rightside)
	{
		ArrayList <Integer> start = new ArrayList<>();
		ArrayList <Integer> end = new ArrayList<>();
		ArrayList <String> predicates = new ArrayList<>();
		ArrayList <Integer> start1 = new ArrayList<>();
		ArrayList <Integer> end1 = new ArrayList<>();
		ArrayList <String> predicates1 = new ArrayList<>();
		int startat,startend1;
		String legend,legend1;
		int begin1,begin2,end2,i,j,m,d,index1,index2,dd;
		//System.out.println(answer+" + "+rightside);
		//endTime=System.nanoTime();
		//long totaltime;
		//totaltime=(endTime-startTime)/1000000000;
		/*if(totaltime>2)
		{
			return answer;
		}*/
		//System.out.println("IN RES");
		for(i=0;i<answer.length();i++)
		{
			if(answer.charAt(i)=='(')
				start.add(i);
			else if(answer.charAt(i)==')')
				end.add(i);
		}
		for(i=0;i<start.size();i++)
		{
			if(i==0)
				predicates.add(answer.substring(0,start.get(i)));
			else
				predicates.add(answer.substring(end.get(i-1)+2,start.get(i)));
		}
		for(i=0;i<rightside.length();i++)
		{
			if(rightside.charAt(i)=='(')
				start1.add(i);
			else if(rightside.charAt(i)==')')
				end1.add(i);
		}
		for(i=0;i<start1.size();i++)
		{
			if(i==0)
				predicates1.add(rightside.substring(0,start1.get(i)));
			else
				predicates1.add(rightside.substring(end1.get(i-1)+2,start1.get(i)));
		}
		//System.out.println("Pres"+predicates);
		//System.out.println("Pres1"+predicates);
		outer:
		for(i=0;i<predicates.size();i++)
		{
			for(j=0;j<predicates1.size();j++)
			{	
				if(predicates.get(i).equals(negation.get(predicates1.get(j))))
				{	//System.out.println("IN if");
					if(i==0)
					{
						index1=0;
					}
					else
					{
						index1=end.get(i-1)+2;
					}
					if(j==0)
					{
						index2=0;
					}
					else
					{
						index2=end1.get(j-1)+2;
					}
					ArrayList<String> aa = new ArrayList<>();
					ArrayList<String> bb = new ArrayList<>();
					for(m=index1;m<answer.length();m++)
					{
						if(answer.charAt(m)=='('||answer.charAt(m)==',')
						{
							startat=m+1;
							m++;
							while(answer.charAt(m)!=','&& answer.charAt(m)!=')')
							{
								m++;
							}
							aa.add(answer.substring(startat,m));
							m=m-1;

						}
						else if(answer.charAt(m)==')')
						{
							break;
						}
					}
					for(m=index2;m<rightside.length();m++)
					{
						if(rightside.charAt(m)=='('||rightside.charAt(m)==',')
						{
							startat=m+1;
							m++;
							while(rightside.charAt(m)!=','&& rightside.charAt(m)!=')')
							{
								m++;
							}
							bb.add(rightside.substring(startat,m));
							m=m-1;
						}
						else if(rightside.charAt(m)==')')
						{
							break;
						}
					}
					//System.out.println(aa);
					//System.out.println(bb);
					int count=0;
					for(m=0;m<aa.size();m++)
					{
						if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='z')
						{
							count++;
						}
						else if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='A' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='Z')
						{
							count++;
						}
						else if(aa.get(m).charAt(0)>='A' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='Z' && bb.get(m).charAt(0)<='z')
						{
							count++;
						}
						else if(aa.get(m).equals(bb.get(m)))
						{
							count++;
						}
					}
					if(count==aa.size())
					{
						//System.out.println("CAN BE UNIFIED AND DO UNIFICATION IN THIS AND RETURN STRING");
						legend=predicates.get(i);
						legend1=predicates1.get(j);
						if(i==0)
						{
							begin1=0;
						}
						else
						{
							begin1=end.get(i-1)+1;
						}
						if(j==0)
						{
							begin2=0;
						}
						else
						{
							begin2=end1.get(j-1)+1;
						}
						HashMap<String,String> change=new HashMap<>();
						for(m=0;m<aa.size();m++)
						{
							if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='z')
							{
								if(!change.containsKey(aa.get(m)))
								{
									change.put(aa.get(m),bb.get(m));
								}
							}
							else if(aa.get(m).charAt(0)>='a' && bb.get(m).charAt(0)>='A' && aa.get(m).charAt(0)<='z' && bb.get(m).charAt(0)<='Z')
							{
								if(!change.containsKey(aa.get(m)))
								{
									change.put(aa.get(m),bb.get(m));
								}
								else if(!change.get(aa.get(m)).equals(bb.get(m)))
								{	
									//System.out.println("Inside 1st else");
									return answer;
								}
							}
							else if(aa.get(m).charAt(0)>='A' && bb.get(m).charAt(0)>='a' && aa.get(m).charAt(0)<='Z' && bb.get(m).charAt(0)<='z')
							{
								if(!change.containsKey(bb.get(m)))
								{	//System.out.println((bb.get(m)+" , "+aa.get(m)));
									change.put(bb.get(m),aa.get(m));
								}
								else if(!change.get(bb.get(m)).equals(aa.get(m)))
								{	
									//System.out.println("Inside 2st else");
									return answer;
								}
							}
							else if(aa.get(m).equals(bb.get(m)))
							{
								//System.out.println("DO NOTHING");
							}
						}
						//System.out.println("Hashmap is");
						//System.out.println(change);
						String aleft,aright;
						for(d=begin1;d<answer.length();d++)
						{
							if(answer.charAt(d)==')')
								break;
						}
						if(d!=answer.length()-1)
						{
							if(answer.substring(0,begin1).equals(""))
								aleft=answer.substring(d+2,answer.length());
							else if(answer.substring(d+2,answer.length()).equals(""))
								aleft=answer.substring(0,begin1);
							else
								aleft=answer.substring(0,begin1)+"|"+answer.substring(d+2,answer.length());

						}
						else
							aleft=answer.substring(0,begin1);
						//System.out.println(aleft);
						for(d=begin2;d<rightside.length();d++)
						{
							if(rightside.charAt(d)==')')
								break;
						}
						//System.out.println("String is "+rightside+"  + "+d+" + "+begin2);
						if(d!=rightside.length()-1)
						{
							if(rightside.substring(0,begin2).equals(""))
								aright=rightside.substring(d+2,rightside.length());
							else if(rightside.substring(d+2,rightside.length()).equals(""))
								aright=rightside.substring(0,begin2);
							else
								aright=rightside.substring(0,begin2)+"|"+rightside.substring(d+2,rightside.length());
							
						}
						else
						{
							aright=rightside.substring(0,begin2);
							//System.out.println("Insisde else 2nd string "+rightside.substring(0,begin2));
						}
						//System.out.println(aright);
						if(aleft.equals(""))
							answer=aright;
						else if(aright.equals(""))
							answer=aleft;
						else
							answer=aleft+"|"+aright;

						//System.out.println("Answer is left "+answer);
						for(d=0;d<answer.length();d++)
						{	//System.out.println(d+" "+answer.charAt(d));
							if(answer.charAt(d)=='('||answer.charAt(d)==',')
							{	dd=d+1;
								//System.out.println(dd);
								while(answer.charAt(dd)!=','&&answer.charAt(dd)!=')')
								{
									
									dd++;
								}
								String aaa = answer.substring(d+1,dd);
								//System.out.println("aaa is "+ aaa);
								if(change.containsKey(aaa))
								{
									String bbb=change.get(aaa);
									answer=answer.substring(0,d+1)+bbb+answer.substring(dd,answer.length());
									//System.out.println("Answer is left and "+answer);
								}
							}
						}
						answer=removeduplicates(answer);
						break outer;
					}
				}
			}
		}
		//System.out.println("AA " + answer);
		return answer;
	}
	
	public static String removeduplicates(String answer)
	{
		ArrayList <Integer> start = new ArrayList<>();
		ArrayList <Integer> end = new ArrayList<>();
		ArrayList <String> predicatesall = new ArrayList<>();
		String answer1="";
		String str,a,b="";
		int i,j,m,start1;
		for(i=0;i<answer.length();i++)
		{
			if(answer.charAt(i)=='(')
				start.add(i);
			else if(answer.charAt(i)==')')
				end.add(i+1);
		}
		for(i=0;i<start.size();i++)
		{
			if(i==0)
				predicatesall.add(answer.substring(0,end.get(i)));
			else
				predicatesall.add(answer.substring(end.get(i-1)+1,end.get(i)));
			//System.out.println("Predicates "+predicatesall.get(i));
		}
		for(i=0;i<predicatesall.size();i++)
		{
			for(j=i+1;j<predicatesall.size();j++)
			{
				if(i!=j && predicatesall.get(i).equals(predicatesall.get(j)))
				{
					predicatesall.remove(j);
				}
			}
		}
		for(i=0;i<predicatesall.size();i++)
		{
			if(i==0)
			{
				answer1=predicatesall.get(0);

			}
			else
				answer1=answer1+"|"+predicatesall.get(i);
		}
		answer=answer1;
		
		return answer;
	}
	
	public static void knowledge()
	{	int i,j;
		String b;
		for(i=0;i<k;i++)
		{
			for(j=0;j<db[i].length()-1;j++)
			{
				if(db[i].charAt(j)=='=' && db[i].charAt(j+1)=='>')
				{	
					b=negate(db[i].substring(0,j));
					db[i]=b+"|"+db[i].substring(j+2,db[i].length());
				}
			}
		}
		for(i=0;i<k;i++)
		{
			//System.out.println(db[i]);
		}
	}
	public static String negate(String a)
	{
		int i;
		int len=a.length();
		String temp1,temp2,temp3;
		if(a.charAt(0)=='~')
		{
			temp1=a.substring(1,len);
			a=temp1;
		}
		else
		{
			temp1=a;
			a="~"+temp1;
		}
	
		for(i=1;i<a.length()-1;i++)
		{
			if(a.charAt(i)=='&')
			{	
				len=a.length();
				a=a.substring(0,i)+"|"+a.substring(i+1,len);
				if(a.charAt(i+1)=='~')
				{
					temp1=a.substring(0,i+1);
					temp2=a.substring(i+2,len);
					a=temp1+temp2;
					i++;
				}
				else
				{
					temp1=a.substring(0,i+1);
					temp2="~";
					temp3=a.substring(i+1,len);
					a=temp1+temp2+temp3;
					i++;
				}
			}
			else if(a.charAt(i)=='|')
			{
				len=a.length();
				a=a.substring(0,i)+"&"+a.substring(i+1,len);
				if(a.charAt(i+1)=='~')
				{
					temp1=a.substring(0,i+1);
					temp2=a.substring(i+2,len);
					a=temp1+temp2;
					i++;
				}
				else
				{
					temp1=a.substring(0,i+1);
					temp2="~";
					temp3=a.substring(i+1,len);
					a=temp1+temp2+temp3;
					i++;
				}
			}
		}
		return a;
	}
	public static void distribute()
	{
		int [] and=new int[100];
		int [] or=new int[100];
		int kk=0,l=0,i,j,m;
		ArrayList<String> s= new ArrayList<>();
		for(i=0;i<k;i++)
		{	//System.out.println(db[i]);
			and=new int[100];
			or=new int[100];
			kk=0;
			l=0;
			for(j=0;j<db[i].length();j++)
			{	
				if(db[i].charAt(j)=='&')
				{
					and[kk]=j;
					kk++;
				}
				else if(db[i].charAt(j)=='|')
				{
					or[l]=j;
					l++;
				}
			}
			//System.out.println("And = "+kk+" Or = "+l );
			if(kk==0 && l==0)
			{
				arr.add(db[i]);
			}
			else if(l==0 && kk!=0)
			{
				for(m=0;m<kk;m++)
				{
					if(m==0)
					{
						arr.add(db[i].substring(0,and[m]));
					}
					// else if(m==kk-1)
					// {
					// 	arr.add(db[i].substring(and[m]+1,db[i].length()));
					// }
					else
					{
						arr.add(db[i].substring(and[m-1]+1,and[m]));
					}

				}
				arr.add(db[i].substring(and[kk-1]+1,db[i].length()));
			}
			else if(l!=0 && kk==0)
			{
				arr.add(db[i]);
			}
			//Now got the position of ands and ors
		}
		//System.out.println("After distribute");
		for(i=0;i<arr.size();i++)
		{	//arr.get(i) = arr.get(i).replaceAll("\\s","");
			//System.out.println(arr.get(i));
		}
	}
}