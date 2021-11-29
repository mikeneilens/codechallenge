package main

import (
	"fmt"
	"testing"
)

func Test_formatingDifferentVlues(t *testing.T) {
	examples := []struct {
		number         int
		expectedResult string
	}{
		{0, ""},
		{1, "1 second"},
		{2, "2 seconds"},
		{60, "1 minute"},
		{120, "2 minutes"},
		{61, "1 minute and 1 second"},
		{62, "1 minute and 2 seconds"},
		{3600, "1 hour"},
		{7200, "2 hours"},
		{3602, "1 hour and 2 seconds"},
		{3662, "1 hour, 1 minute and 2 seconds"},
		{273660, "3 days, 4 hours and 1 minute"},
		{94609440, "3 years and 24 minutes"},
	}

	for _, example := range examples {
		result := formatTime(example.number)
		if result != example.expectedResult {
			t.Fatalf(`formtTime(%v) should return "%s" but returned "%s"`, example.number, example.expectedResult, result)
		} else {
			fmt.Printf("passed - formtTime(%v) returned \"%s\" \n", example.number, result)
		}
	}
}
