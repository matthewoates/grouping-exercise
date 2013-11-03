grouping-exercise
=================

A programming exercise based on data matching

Notes for future enhancements
=================

Improve parsing of phone numbers and emails.

Services like gmail consider foo.bar@gmail.com, foobar@gmail.com, and even foobar+wtf@gmail.com to be the same address. While use of the '+' may be very unlikely, the periods may be frequently omitted.

Our algorithm does not pay attention to country codes. Therefore, it thinks:
+1 123 456 7890
and
123 456 7890

are different phone numbers.


Dependencies
=================
requires java 1.6
