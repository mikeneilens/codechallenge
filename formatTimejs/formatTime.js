const Unit = Object.freeze({
    second: { secondsPerUnit:1, maxValue:60, text:"second"},
    minute: { secondsPerUnit:60, maxValue:60, text:"minute"},
    hour: { secondsPerUnit:3600, maxValue:24, text:"hour"},
    day: { secondsPerUnit:86400, maxValue:365, text:"day"},
    year: { secondsPerUnit:31536000, maxValue:100, text:"year"}
});

class UnitTime {
    constructor(unit, quantity) {
        this.unit = unit;
        this.value = quantity;
    }
    text() {
        if (this.value > 1) {
            return `${this.value} ${this.unit.text}s`;
        } else {
            return `${this.value} ${this.unit.text}`;
        }
    }
}

function formatTime(timeInSeconds) {

    function isNonZeroTime(unitTime) {
        return unitTime.value > 0;
    }

    const calcualtedUnitTimes = calcTime(timeInSeconds).filter(isNonZeroTime);
    return formatIntoText(calcualtedUnitTimes);
}

function calcTime(timeInSeconds) {

    function calcTimePerUnit(unit) {
        const value = Math.floor(timeInSeconds/unit.secondsPerUnit) % unit.maxValue; 
        return new UnitTime(unit, value);
      }
      return [Unit.year, Unit.day, Unit.hour, Unit.minute, Unit.second].map(calcTimePerUnit);
}

function formatIntoText(unitTimes) {
    switch (unitTimes.length) {
        case 0: 
          return "none"; break;
        case 1: 
          return `${unitTimes[0].text()}`;
        case 2: 
          return `${unitTimes[0].text()} and ${unitTimes[1].text()}`;
        case 3: 
          return `${unitTimes[0].text()}, ${unitTimes[1].text()} and ${unitTimes[2].text()}`;
        case 4: 
          return `${unitTimes[0].text()}, ${unitTimes[1].text()}, ${unitTimes[2].text()} and ${unitTimes[3].text()}`;
        case 5: 
          return `${unitTimes[0].text()}, ${unitTimes[1].text()}, ${unitTimes[2].text()}, ${unitTimes[3].text()} and ${unitTimes[4].text()}`; 
    }
}

function assertTrue(expected, actual) {
    if (expected != actual) {
        throw new Error(`Test failed. Expected '${expected}' but actual is '${actual}' `);
    }
}

assertTrue(formatTime(0), "none");
assertTrue(formatTime(1), "1 second");
assertTrue(formatTime(2), "2 seconds");
assertTrue(formatTime(60), "1 minute"  );
assertTrue(formatTime(120), "2 minutes" );
assertTrue(formatTime(3600), "1 hour");
assertTrue(formatTime(86400), "1 day");
assertTrue(formatTime(31536000), "1 year");
assertTrue(formatTime(31536002), "1 year and 2 seconds");
assertTrue(formatTime(31536062), "1 year, 1 minute and 2 seconds");
assertTrue(formatTime(31539662), "1 year, 1 hour, 1 minute and 2 seconds");
assertTrue(formatTime(31712815), "1 year, 2 days, 1 hour, 6 minutes and 55 seconds");
