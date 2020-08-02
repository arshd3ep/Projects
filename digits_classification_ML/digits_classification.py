from tensorflow import keras
import matplotlib.pyplot as plt
import numpy as np

data = keras.datasets.mnist

(X_train, Y_train), (X_test, Y_test) = data.load_data()

"""
for 000n range(4):
    plt.subplot(221+i)
    plt.imshow(X_train[i], cmap=plt.get_cmap('gray'))
plt.show()
"""
class_names = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]

num_pixels = X_train.shape[1] * X_train.shape[2]

X_train = X_train/255
X_test = X_test/255

X_val = X_train[:5000]
Y_val = Y_train[:5000]

X_train = X_train[5000:]
Y_train = Y_train[5000:]

model = keras.Sequential([keras.layers.Flatten(input_shape=(28, 28)),
                         keras.layers.Dense(128, activation='relu'),
                         keras.layers.Dense(10, activation='softmax')
                          ])
model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

model.fit(X_train, Y_train, validation_data=(X_val, Y_val), epochs=10)

test_loss, test_acc = model.evaluate(X_test, Y_test)

print("Tested accuracy", test_acc)


"""
model = keras.models.load_model("digits_classification.h5")
"""

prediction = model.predict(X_test)

plt.figure(figsize=(10, 10))
for i in range(25):
    plt.subplot(5, 5, i+1)
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])
    plt.imshow(X_test[i], cmap=plt.cm.get_cmap(('gray')))
    plt.xlabel("Actual:" + class_names[Y_test[i]])
    plt.ylabel("Predicted:" + class_names[np.argmax(prediction[i])])
plt.show()
