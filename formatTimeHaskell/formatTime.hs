createListOfTimes :: Integer -> [(String, Integer)]
createListOfTimes timeInSeconds = [(unit, mod (div timeInSeconds  secondsPerUnit) maxValue )  | (unit, secondsPerUnit, maxValue) <- [("year",31536000,1000),("day",86400,365),("hour",3600,24),("minute",60,60),("second",1 ,60)] ]

addSuffixToPlural :: (String, Integer) -> (String, Integer)
addSuffixToPlural (unit, duration) = if duration > 1 then (unit ++ "s", duration) else (unit, duration)


formatText :: [(String, Integer)] -> String
formatText [(u1,d1), (u2,d2), (u3,d3), (u4, d4), (u5,d5)] = show d1 ++ " " ++ u1 ++ ", " ++ show d2 ++ " " ++ u2 ++ ", " ++ show d3 ++ " " ++ u3 ++ ", " ++ show d4 ++ " " ++ u4 ++ " and " ++ show d5 ++ " " ++ u5
formatText [(u1,d1), (u2,d2), (u3,d3), (u4, d4)] = show d1 ++ " " ++ u1 ++ ", " ++ show d2 ++ " " ++ u2 ++ ", " ++ show d3 ++ " " ++ u3 ++ " and " ++ show d4 ++ " " ++ u4
formatText [(u1,d1), (u2,d2), (u3,d3)] = show d1 ++ " " ++ u1 ++ ", " ++ show d2 ++ " " ++ u2 ++ " and " ++ show d3 ++ " " ++ u3
formatText [(u1,d1), (u2,d2)] = show d1 ++ " " ++ u1 ++ " and " ++ show d2 ++ " " ++ u2 
formatText [(u1,d1)] = show d1 ++ " " ++ u1 


formatTime :: Integer -> String
formatTime timeInSeconds = formatText( filter (\(_,duration) -> duration  /= 0)  ( map(addSuffixToPlural) (createListOfTimes timeInSeconds) ) )


test :: [String]
test  = [formatTime timeInSeconds | timeInSeconds <- [1, 2, 60, 120, 3600, 3601, 86400, 31536000, 123456789]]