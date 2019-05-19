<?php
//must provide search=<search string>
//optionals are realAle (set to either yes or no), page (set to the page number).
//if search string is set to nearby then must provide lat and lng for latitude and longitude geographic co-ordinates.
$search=$_GET["search"]; $search=str_replace(" ","+",$search);
$realAle=$_GET["realAle"];
$pubs=$_GET["pubs"];
$uId=$_GET["uId"];
$debug=$_GET["debug"];
$memberDiscount=$_GET["memberDiscount"];
$garden=$_GET["garden"];
$lmeals=$_GET["lmeals"];
$emeals=$_GET["emeals"];
$emeals=$_GET["events"];
$pageNo=$_GET["page"]; if ($pageNo=="") { $pageNo=1; }
$webserviceURL1="https://pubcrawlapi.appspot.com/pub/";
$webserviceURL2="https://pubcrawlapi.appspot.com/listofpubs/";

$querystring="";
if ($realAle=="yes") {
	$aleFeature="%2CRealAle";
}
if ($pubs=="yes") {
	$pubFeature="%2CPub";
}
if($memberDiscount=="yes") {
	$memberDiscountFeature="%2CMemberDiscountScheme";
}
if($garden=="yes") {
	$gardenFeature="%2CGarden";
}
if($lmeals=="yes") {
	$lmealsFeature="%2CLunchtimeMeals";
}
if($emeals=="yes") {
	$emealsFeature="%2CEveningMeals";
}
if($events=="yes") {
	$eventsFeature="%2CEvents";
}
if ($search=="nearby") {
	$listTitle = "Pubs nearby";
} else {
	$listTitle = $search;
}

$querystring="q=qqqq&t=ft&p=pppp&alt=&features=Open$aleFeature$pubFeature$memberDiscountFeature$gardenFeature$lmealsFeature$emealsFeature$eventsFeature";

if ($search=="nearby") {
	$lat=$_GET["lat"];
	$lng=$_GET["lng"];
	$loc=str_replace("xxxx",$lat,"&lat=xxxx&lng=yyyy");
	$loc=str_replace("yyyy",$lng,$loc);
	$querystring=$querystring.$loc;
	$querystring=str_replace("&t=ft","&t=d",$querystring);
}
$querystring=str_replace("qqqq",$search,$querystring);
$querystring=str_replace("pppp",$pageNo,$querystring);

// Create connection
include_once "connection.php";
$conn = getConnection();

// Check connection
if (!$conn) {
	$messageJson = "{".jsExp("Status","-999","",",").js("Text","Can't connect.","","")."}";
	$jsonOut= "{" .jsExp("Message",$messageJson,"","")."}";
	echo $jsonOut;
	die("Connection failed: " . mysqli_connect_error());
}
$sql = "SELECT json FROM SearchCache WHERE search='aaaaaaaa' AND fullSearch='bbbbbbbb' AND uId='uuuuuuuu'";
$sql = str_replace("aaaaaaaa",$search, $sql);
$sql = str_replace("bbbbbbbb",$querystring, $sql);
$sql = str_replace("uuuuuuuu",$uId, $sql);
$result = $conn->query($sql)->fetchAll();

if ((count($result) > 0) && ($debug != "y")) {
	foreach ($result as $row) {
		$json=$row["json"];
	}
	$json = addDynamicData($search, $json, $uId, $pageNo, $lat, $lng, $uId, $pubs, $realAle, $memberDiscount, $garden, $lmeals, $emeals, $events, $webserviceURL2);
	echo $json;
	$conn->close();
} else {
	$url="http://whatpub.com/search?".$querystring;
	$data = file_get_contents($url);
	$data = str_replace("<br>","<br/>",$data);
	$data = str_replace("<br/>"," ",$data);
	$data = str_replace("&amp","***amp",$data);
	$data = str_replace("&","",$data);
	$data = str_replace("***amp","&amp",$data);
	$imgFound = false;
	$firstSectionFound = false;
	$firstChar=0;
	$lastChar=0;
	$dataOut="";
	$chars = str_split($data);

	//add closing tag to <img> elements as they are always unclosed
	for($x = 0; $x < ( count($chars) - 20) ; $x++) {
		$wordToCheck = $chars[$x]  . $chars[$x+1] . $chars[$x+2] . $chars[$x+3];
		if ($wordToCheck == "<img") {
			$imgFound = true;
		}
		if (($chars[$x]==">") && $imgFound) { 
			$dataOut = $dataOut . "/"; 
			$imgFound = false;
		}
		$dataOut = $dataOut . $chars[$x];
	}
	
	//The stuff we are interested in is the content in all of the <section> elelemnts"
	$chars = str_split($dataOut);
	$showingResultsFound = false; $showingResultsStart = -1; $showingResultsEnd = -1;
	for($x = 0; $x < ( count($chars) - 20) ; $x++) {
		$sectionCheck = $chars[$x]  . $chars[$x+1] . $chars[$x+2] . $chars[$x+3] . $chars[$x+4] . $chars[$x+5] . $chars[$x+6] . $chars[$x+7];
		$sectionEndCheck = $sectionCheck. $chars[$x+8];
		$showingResultCheck = $sectionCheck  . $chars[$x+8] . $chars[$x+9] . $chars[$x+10] . $chars[$x+11] . $chars[$x+12] . $chars[$x+13] . $chars[$x+14];
									
		if (($sectionCheck == "<section") && ($firstSectionFound == false)) {
			$firstSectionFound = true;
			$firstChar = $x;
		}
		if  ($sectionEndCheck == "</section" )  {
			$lastChar = $x+10;
		}
		if  ($showingResultCheck == "showing_results" )  {
			$showingResultsFound=true;
		}
		if (($showingResultsFound) && ($chars[$x]==">") &&($showingResultsStart<0) ) {
			$showingResultsStart=$x + 1;
		}
		if (($showingResultsFound) && ($chars[$x]=="<") && ($showingResultsEnd<0) )  { 
			$showingResultsEnd=$x; 
		}
	}
	//get the number of pages from the "showing results" string
	if ($showingResultsFound) {
		$total= explode("of ", substr($dataOut, $showingResultsStart, $showingResultsEnd - $showingResultsStart));
		$total = str_replace(",","",$total);
		$noOfPages=intval($total[1] / 10) + 1; 
	} else {
		$noOfPages=99;
	}
	
	$length = $lastChar - $firstChar;
	$dataOut = substr($dataOut, $firstChar, $length);
	$dataOut = "<pubList>" . $dataOut . "</pubList>";
	
	//convert the XML into an array
	$xml=simplexml_load_string($dataOut);
	$json = json_encode($xml);
	$array = json_decode($json,TRUE);

	//The array is structured as follows for the sample HTML:
	//
	//<element0>
	//    <element1>a</element1>
	//    <element2>b</element2>
	//    <element3>c</element3>
	//        <element3.1>d</element3.1>
	//    </element3>
	//</element0> 
	//
	//  array("element0","value")
	//  "value" is array({"element1","a"},{"element2","b"},"Value2")
	//  "value2" is {"element 3.1","d"}  
	
	$section = -1; 
	$id=array(); 
	$branch=array(); 
	$name=array(); 
	$distance=array(); 
	$town=array(); 
	$town2=array(); 
	function readArray ($level, $content, $element, $singlePub)
	{
	//Each item in the associative array is {"element","content"}
	//If the content for an element is another array of elements then the function calls itself to drill down into the next block of content
	//Otherwise it inspects the content to see if it has the data we are looking for.
	//n.b. arributes are treated as blocks of content so "class"="classname" wihtin an element is equivalent to <class>classname</class> within the content of the element.
		foreach($content as $x	=> $content2) 	
		{
			global $section, $id, $branch, $name, $distance, $town, $town2;
		
				$element[$level] = $x; 
				if (is_array($content2) ) {
					readArray($level+1, $content2, $element, $singlePub);
				}
				else
				{
//					for($i = 0; $i < count($element); $i++) {
//						echo $element[$i] . ":";
//					}
//					echo " value =" . $content2;
//					echo "<br> #". $element[1];
					switch ($level) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							if ($singlePub!="N") {
								if (($element[2]=="p") )
								{
									$ndx=0;
									$town2[$ndx]=removeDuffStrings($content2);
								}
							}
							break;
						case 3:
							if ($singlePub=="N") { 
								if (($element[3]=="p") && ($element[4]==0))
								{
									$ndx=$element[1];
									$town2[$ndx]=removeDuffStrings($content2);
								}
							} else {
								if (($element[2]=="h2") && ($element[3]=="a"))
								{
									$ndx=0;
									$name[$ndx]=removeDuffStrings($content2);
								}
								if (($element[3]=="p") )
								{
									$ndx=0;
									$town[$ndx]=removeDuffStrings($content2);
								}
							}
							break;
						case 4:
							if ($singlePub=="N") { 
								if (($element[3]=="h2") && ($element[4]=="a"))
								{
									$ndx=$element[1];
									$name[$ndx]=removeDuffStrings($content2);
								}
								if (($element[3]=="p") && ($element[4]==0))
								{
									$ndx=$element[1];
									$distance[$ndx]=$content2;
								}
								if (($element[3]=="p") && ($element[4]==1))
								{
									$ndx=$element[1];
									$town[$ndx]=removeDuffStrings($content2);
								}
							}
							else {
								if ($element[4]=="data-pub-id")
								{
									$s = explode("/",$content2,2);
									$ndx=0;
									$branch[$ndx]=$s[0];$id[$ndx]=$s[1];
								}
							}
							break;
						case 5:
							if ($singlePub=="N") { 
								if ($element[5]=="data-pub-id")
								{
									$s = explode("/",$content2,2);
									$ndx=$element[1];
									$branch[$ndx]=$s[0];$id[$ndx]=$s[1];
								}
							}
							break;										
						case 6:
							break;										
						case 7:
							break;										
						default:
							break;
					}
				}
		}
	}
	
	readArray(0, $array, array(), "N");
	
	if (count($name)==0) {
		$noOfPages=1;
		readArray(0, $array, array(), "Y");
	}
	
	$jsonOut="";
	$noOfPubs = count($name);
	$pubJson=array();
	if ($noOfPubs != 0) {
		$jsonOut="{";
		for($x = 0; $x < $noOfPubs; $x++) {
			$pubJson[$x]  = "{";
			if ($town[$x]=="") { $town[$x]=$town2[$x]; }
			$pubJson[$x] .= js("Id",$id[$x],"","");
			$pubJson[$x] .= js("Branch",$branch[$x],",","");
			$pubJson[$x] .= js("Name",$name[$x],",","");
			$pubJson[$x] .= js("Distance",$distance[$x],",","");
			$pubJson[$x] .= js("Town",$town[$x],",","");
			$pubJson[$x] .= js("PubService",$webserviceURL1."?v=1&id=".$id[$x]."&branch=".$branch[$x]."&uId=".$uId."&pubs=".$pubs."&realAle=".$realAle."&memberDiscount=".$memberDiscount."&garden=".$garden."&lmeals=".$lmeals."&emeals=".$emeals."&town=".str_replace(" ","+",$town[$x]),",","");
			$pubJson[$x] .= "}";
		}	
		$jsonOut .= arrayJsExp("Pubs",$pubJson,"","");
		$jsonOut .= jsExp("PageNo",$pageNo,",","");
		$jsonOut .= jsExp("NoOfPages",$noOfPages,",","");
		$jsonOut .= js("ListTitle",$listTitle,",","");
		$jsonOut .= js("MorePubsService","morePubsService",",","");

		$messageJson = "{".jsExp("Status","0","",",").js("Text","Pubs retrieved.","","")."}";
		$jsonOut .= jsExp("Message",$messageJson,",","");
		$jsonOut=$jsonOut."}";
		
		$sql = "INSERT INTO SearchCache (search,fullSearch,json, uid) VALUES ('aaaaaaaa','bbbbbbbb','cccccccc','uuuuuuuu')";
		$sql = str_replace("aaaaaaaa",$search, $sql);
		$sql = str_replace("bbbbbbbb",$querystring, $sql);
		$sql = str_replace("cccccccc",$jsonOut, $sql);
		$sql = str_replace("uuuuuuuu",$uId, $sql);
		$result = $conn->query($sql);
		
	} else {
		$messageJson = "{".jsExp("Status","-1","",",").js("Text","Pubs not found.","","")."}";
		$jsonOut= "{" .jsExp("Message",$messageJson,"","")."}";
	}
	$jsonOut = addDynamicData($search, $jsonOut, $uId, $pageNo, $lat, $lng, $uId, $pubs, $realAle, $memberDiscount, $garden, $lmeals, $emeals, $events, $webserviceURL2);
	echo $jsonOut;
	
	$conn->close();
}

function addDynamicData($search, $json, $uId, $pageNo, $lat, $lng, $uId, $pubs, $realAle, $memberDiscount, $garden, $lmeals, $emeals, $events, $url) {
	$json_decode = json_decode($json);
	$noOfPages = $json_decode ->NoOfPages;
	if ($pageNo < $noOfPages) {
		$morePubsService = $url."?search=".$search."&page=".($pageNo + 1)."&lat=".$lat."&lng=".$lng."&uId=".$uId."&pubs=".$pubs."&realAle=".$realAle."&memberDiscount=".$memberDiscount."&garden=".$garden."&lmeals=".$lmeals."&emeals=".$emeals."&events=".$events;		
	} else {
		$morePubsService = "";
	}
	$json = str_replace("morePubsService",$morePubsService, $json);
	return $json;
}

function removeDuffStrings($stringIn)
{
	$stringOut=str_replace("#039;","'", $stringIn);
	$stringOut=str_replace(" pound;"," £", $stringOut);
	$stringOut=str_replace(" & "," and ", $stringOut);
	$stringOut=str_replace("&"," and ", $stringOut);
	return $stringOut;
}
function js($field,$data,$leadingComma,$trailingComma) {
	// return $field. $data as "$field":"$data" with leading and/or trailing commas.
	 return $leadingComma.chr(34).$field.chr(34).":".chr(34).$data.chr(34).$trailingComma;
}
function jsExp($field,$data,$leadingComma,$trailingComma) {
	// return $field. $data as "$field":$data with leading and/or trailing commas. Use this if $data is a Json expression or a number
	 return $leadingComma.chr(34).$field.chr(34).":".$data.$trailingComma;
}
function arrayJs($field,$data,$leadingComma,$trailingComma) {
	// return $field. [$data1,$data2,$data3] as "$field":["$data1","$data2","$data3"] with leading and/or trailing commas.
	return arrayJs2($field,$data,$leadingComma,$trailingComma,chr(34));
}
function arrayJsExp($field,$data,$leadingComma,$trailingComma) {
	// return $field. [$data1,$data2,$data3] as "$field":[$data1,$data2,$data3] with leading and/or trailing commas. Use this if $data contains Json expressions or numbers
	return arrayJs2($field,$data,$leadingComma,$trailingComma,"");
}
function arrayJs2($field,$data,$leadingComma,$trailingComma,$quote) {
	$json="";
	if (count($data)>0) {
		$json=$leadingComma.chr(34).$field.chr(34).":[";
		for($x = 0; $x < count($data); $x++) {
			if ($x > 0) { $json = $json . ","; }
			$json .= $quote.$data[$x].$quote;  
		}	
		$json .= "]".$trailingComma;
	}
	return $json;
}
?>
