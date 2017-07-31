### Conclusion ###
**Always check if a library or module exists before you try to make it yourself.**

### Purpose ###
The purpose of these little programs is to test the performance of different methods of accessing pixels to get the average RGB value of the image.

I wrote a small shell script, ```avgtime.sh``` to run any command ```n``` times to find the average run time of the command.  The script returns two average times: the "real time" and the "CPU-seconds" as described by the manual page for ```time```.  I have found the accuracy of the CPU-seconds to be better at higher resolutions and the inverse is true for the real time.  What this script lacks in precision will make up for inconsistencies across the different languages with their timing.

All these results will be the most relevant output from the script

### First Set of Results ###

Source files from ```srcv1```.

Resolution in pixels.  Time in seconds.

| Image Resolution | Non-Threaded, Python | Non-Threaded, Java|Threaded, Java |
|:----------------:|:--------------------:|:-----------------:|:-------------:|
| 8x4              | 0.04                 | 0.09              | 0.09          |
| 1280x712         | 2.67                 | 0.12              | 0.13          |
| 1920x1080        | 5.85                 | 0.15              | 0.16          |
| 3264x2448        | 23.54                | 2.11              | 2.15          |
| 10315x7049       | 207.89               | 1.61              | 1.89          |
| 9000x9000        | 222.61               | 2.74              | 3.09          |

I have found that my python script is dreadfully inefficient and my threaded java algorithm probably spends too much time breaking the picture down into different parts.

The next iteration will attempt to address these issues.

### Second Set of Results ###

Source files from ```srcv2```.

Resolution in pixels.  Time in seconds.

| Image Resolution | Python OpenCV   | C++ OpenCV  | Nested For Loop Java | Bitwise Access Java  |
|:----------------:|:---------------:|:-----------:|:--------------------:|:--------------------:|
| 8x4              | 0.07            | 0.00        | 0.09                 | 0.09                 |
| 1280x712         | 0.07            | 0.01        | 0.12                 | 0.11                 |
| 1920x1080        | 0.09            | 0.01        | 0.15                 | 0.12                 |
| 3264x2448        | 0.28            | 0.20        | 2.08                 | 1.99                 |
| 10315x7049       | 0.48            | 0.36        | 1.61                 | 0.77                 |
| 9000x9000        | 0.59            | 0.47        | 2.76                 | 1.81                 |

In the time between the first set of tests and this set, I found an image library called OpenCV that runs in Python, C++, and Java (not well documented in Java, so I did not get it to work).  This library includes a method called mean() that does exactly what I've been trying to do with python and Java, but with their own image data structure.

**The first two columns show the performance boost that an existing library can give to a project.**

After finding OpenCV, I dropped the threaded Java in favor of a non-nested for loop algorithm using Data Buffer Bytes from Java's Buffered Image which skips a set of function calls per red, green, and blue value in a pixel.

### Usage ###

#### srcv1 ####

* ```python nested-for-loop-avg.py image_file.png```
* ```java ForLoopAvg imageFile.png```
* ```java ThreadedAvg imageFile.jpg```

#### srcv2 ####

* ```python opencv_avg.py imageFile.jpg```
* ```java QuickForLoopAvg imageFile.jpg```
* ```./AverageColor imageFile.jpg```
