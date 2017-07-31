import cv2
import numpy as np
import sys

def main():
    for infile in sys.argv[1:]:
        try:
            im = cv2.imread(infile)
            avg = find_avg(im)
            print(avg)
        except IOError:
            print("Cant read the file")
        return

def find_avg(im):
    rgbavg = []
    average = cv2.mean(im)
    for i in range(3):
        rgbavg.append(int(average[2-i]))
    return rgbavg

main()
