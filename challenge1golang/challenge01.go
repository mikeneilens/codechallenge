package main

import (
	"fmt"
)

func main() {

	fmt.Println("hello world")
}

var timeUnits = []struct {
	unit          string
	secondsInUnit int
}{
	{"year", 365 * 24 * 60 * 60},
	{"day", 24 * 60 * 60},
	{"hour", 60 * 60},
	{"minute", 60},
	{"second", 1},
}

func formatTime(number int) string {
	seconds := number
	result := ""

	for _, timeUnit := range timeUnits {
		noOfUnits := seconds / timeUnit.secondsInUnit
		if noOfUnits > 0 {
			seconds -= noOfUnits * timeUnit.secondsInUnit
			updateResult(&result, noOfUnits, timeUnit.unit, seconds)
		}
	}
	return result
}

func updateResult(result *string, number int, interval string, remainingSeconds int) {
	timeText := fmt.Sprintf("%v %s", number, interval)
	pluraliseText(&timeText, number)
	concatonateTime(result, timeText, remainingSeconds)
}

func pluraliseText(text *string, number int) {
	if number > 1 {
		*text += "s"
	}
}

func concatonateTime(existingTime *string, newTime string, remainingSeconds int) {
	if *existingTime == "" {
		*existingTime = newTime
	} else {
		if remainingSeconds == 0 {
			*existingTime = *existingTime + " and " + newTime
		} else {
			*existingTime = *existingTime + ", " + newTime
		}
	}
}
