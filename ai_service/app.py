import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder # <-- This is the important fix
import pickle
from flask import Flask, request, jsonify
from flask_cors import CORS

# --- 1. MODEL TRAINING ---
def train_model():
    data = pd.read_csv('Training.csv')
    X = data.iloc[:, :-1]
    y = data.iloc[:, -1]

    # Convert text disease names into numbers
    label_encoder = LabelEncoder()
    y_encoded = label_encoder.fit_transform(y)

    # Train on the numerical data
    X_train, X_test, y_train_encoded, y_test_encoded = train_test_split(X, y_encoded, test_size=0.2, random_state=42)
    dt_model = DecisionTreeClassifier()
    dt_model.fit(X_train, y_train_encoded)

    # Save the model
    with open('disease_model.pkl', 'wb') as f:
        pickle.dump(dt_model, f)

    # Save the columns
    with open('model_columns.pkl', 'wb') as f:
        pickle.dump(X.columns.tolist(), f)

    # Save the encoder
    with open('label_encoder.pkl', 'wb') as f:
        pickle.dump(label_encoder, f)

    print("Model trained and saved successfully!")

# --- 2. FLASK API ---
app = Flask(__name__)
CORS(app)

try:
    model = pickle.load(open('disease_model.pkl', 'rb'))
    model_columns = pickle.load(open('model_columns.pkl', 'rb'))
    label_encoder = pickle.load(open('label_encoder.pkl', 'rb'))
except FileNotFoundError:
    print("Model not found. Please run the training script first.")
    model = None
    model_columns = None
    label_encoder = None

@app.route('/predict', methods=['POST'])
def predict():
    if model is None:
        return jsonify({'error': 'Model not trained.'}), 500

    symptoms = request.json['symptoms']
    input_data = pd.DataFrame([symptoms], columns=model_columns)
    prediction_encoded = model.predict(input_data)
    predicted_disease = label_encoder.inverse_transform(prediction_encoded)

    return jsonify({'predicted_disease': predicted_disease[0]})

if __name__ == '__main__':
    import sys
    if len(sys.argv) > 1 and sys.argv[1] == 'train':
        train_model()
    else:
        app.run(debug=True, port=5000)