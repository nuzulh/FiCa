# Steps of building model

1. Data

    We collect data of image koi fish from the google. There are about 500 image for 10 species, so 50 image of each species
    
    GDrive : https://drive.google.com/drive/folders/174T_vIH54M8Bozu42AqCSG96HHiT_J1m?usp=sharing
    
2. Pre-processing Data
    
    The data we process, we convert the format image into jpg/png and then we split data for training, validation and test with 70,15,15 ratio. We create image data generator for normalized and augmentation data.
 
3. Train model
    For the model we use transfer learning technique based mobilenetv2. We use all layers from mobilenet and then we build dense layer and drop layer.

4. Evaluating model
    
    ![acc](https://user-images.githubusercontent.com/78861020/173117631-9d7d909e-8cff-4f9c-b101-0ec02f30a207.png)
    
    for the test accuracy we archieve about 94%
    
