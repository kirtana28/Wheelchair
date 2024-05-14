#include <Servo.h>

Servo myservo;  // create servo object to control a servo

int pos = 0;

char t;

void setup() {
  pinMode(8, OUTPUT);  //left motors forward
  pinMode(9, OUTPUT);  //left motors reverse
  pinMode(10, OUTPUT);  //right motors forward
  pinMode(11, OUTPUT);  //right motors reverse
  pinMode(7, OUTPUT);
  myservo.attach(5);
  Serial.begin(9600);

}

void loop() {
  if (Serial.available()) {
    t = Serial.read();
    Serial.println(t);
  }

  if (t == 'F') {          //move forward(all motors rotate in forward direction)
    digitalWrite(8, HIGH);
    digitalWrite(9, LOW);
    digitalWrite(10, HIGH);
    digitalWrite(11, LOW);
  }

  else if (t == 'B') {    //move reverse (all motors rotate in reverse direction)
    digitalWrite(8, LOW);
    digitalWrite(9, HIGH);
    digitalWrite(10, LOW);
    digitalWrite(11, HIGH);
  }

  else if (t == 'L') {    //turn right (left side motors rotate in forward direction, right side motors doesn't rotate)
    digitalWrite(8, HIGH);
    digitalWrite(9, LOW);
    digitalWrite(10, LOW);
    digitalWrite(11, LOW);
  }

  else if (t == 'R') {    //turn left (right side motors rotate in forward direction, left side motors doesn't rotate)
    digitalWrite(8, LOW);
    digitalWrite(9, LOW);
    digitalWrite(10, HIGH);
    digitalWrite(11, LOW);
  }

  else if (t == 'S') {    //turn left (right side motors rotate in forward direction, left side motors doesn't rotate)
    digitalWrite(8, LOW);
    digitalWrite(9, LOW);
    digitalWrite(10, LOW);
    digitalWrite(11, LOW);
  }

  else if (t == 'W') {  //turn led on or off)
    digitalWrite(7, HIGH);
  }
  else if (t == 'w') {
    digitalWrite(7, LOW);
  }



  else if (t == 'A') {

    for (pos = 0; pos <= 45; pos += 1) { // goes from 0 degrees to 180 degrees
      // in steps of 1 degree
      myservo.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);                       // waits 15 ms for the servo to reach the position
    }
    for (pos = 45; pos >= 0; pos -= 1) { // goes from 180 degrees to 0 degrees
      myservo.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);                       // waits 15 ms for the servo to reach the position
    }

  }
  else if (t == 'a') {

    myservo.write(0);
  }



  delay(100);
}