### Purpose ###
The purpose of these little programs is to test the performance of threaded vs. non-threaded workloads.  In this case, it's calculating the average RGB value for an image.

### First Set of Results ###

Source files from ```srcv1```.

Resolution in pixels.  Time in seconds.

| Image Resolution | Non-Threaded, Python | Non-Threaded, Java|Threaded, Java |
|:----------------:|:--------------------:|:-----------------:|:-------------:|
| 8x4              | 0.016793             | 0.049528          | 0.056453      |
| 1280x712         | 2.5957               | 0.086073          | 0.096396      |
| 1920x1080        | 5.6488               | 0.11205           | 0.12189       |
| 3264x2448        | 21.785               | 2.0474            | 2.0986        |
| 10315x7049       | 198.43               | 1.5615            | 1.8167        |
| 9000x9000        | 234.08               | 2.7034            | 3.0414        |

I have found that my python script is dreadfully inefficient and my threaded java algorithm probably spends too much time breaking the picture down into different parts.

The next iteration will attempt to address these issues.

### Usage ###

```python nested-for-loop-avg.py image_file.png```

```java ForLoopAvg imageFile.png```

```java ThreadedAvg imageFile.jpg```
