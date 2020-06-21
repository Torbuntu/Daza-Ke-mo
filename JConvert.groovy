import groovy.json.JsonOutput

class JConvert {
	
	static def letters = []
	static def entry
	public static void main(String... args){
	
		new File("daza_kemo_dict.txt").eachLine{ line -> 
			if(line.trim().size() > 0) {
				line.trim()
				if(line.contains("::::")) {
					line = line.replace("::::", "").trim()
					entry = new LetterEntry(letter: line, content: [])
					letters.add(entry)
				} else {
					def item = line.split("-")
					def letterMap = [item[0].trim(), item[1].trim().replace("\"", "\\\"")]
					entry.content.add(letterMap)					
				}
			}
		}
		
		new File("dictionary.json").withWriter{ out ->
			out.println("{")
			letters.each{
				out.println "\"Letter\": \""+it.letter+"\", \n\"Content\":["
				it.content.each{ en ->
					out.println("\t{\""+ en[0] +"\": \""+ en[1] +"\"},")
				}
				out.println("],")
			}
			out.println("}")
		}
		
	}

}

class LetterEntry{
	String letter
	def content
}
