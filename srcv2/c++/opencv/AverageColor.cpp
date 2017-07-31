#include <iostream>
#include <ctime>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

int main(int argc, char** argv){
    if(argc != 2){
        cout << "Usage: " << argv[0] << " <filename>\n";
        return -1;
    }

    Mat image;
    image = imread(argv[1], 1);

    if(!image.data){
        printf("No image data \n");
        return -1;
    }

//    clock_t start = clock();
    static unsigned char rgbavg [3];

    Scalar average = mean(image);

    for(int i = 0; i < 3; i++){
        rgbavg[2-i] = (unsigned char) average.val[i];
    }

//    double duration = static_cast<double>(clock() - start) / CLOCKS_PER_SEC;

//    printf("Time elapsed: %f seconds\n", duration);

    cout << "[";
    for(int i = 0; i < 3; i++){
        if(i < 2)
            printf("%u, ", rgbavg[i]);
        else
            printf("%u", rgbavg[i]);
    }
    cout << "]\n";

    return 0;
}

