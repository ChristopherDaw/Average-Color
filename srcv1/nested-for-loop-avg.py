from PIL import Image
import numpy
import sys
from time import time

def main():
    for infile in sys.argv[1:]:
        try:
            im = Image.open(infile)
            start_time = time()
            find_avg(im)
            print("Time elapsed: {} seconds.".format(time() - start_time))
        except IOError:
            print("Cant read the file")
        return

def find_avg(im):
    size = im.size
    numpixels = size[0] * size[1]
    pic = im.load()
    rgb_total = (0, 0, 0)
    for x in range(size[0]):
        for y in range(size[1]):
            rgb_total = tuple(numpy.add(rgb_total, pic[x, y]))

    avg_color = tuple(numpy.divide(rgb_total, numpixels))

    print(avg_color)
    display_color = Image.new("RGB", (250, 250), avg_color)
    display_color.show()
    display_color.save("output.png")

main()
