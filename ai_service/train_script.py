import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
import pickle

print("--- Starting the final training process ---")

# 1. Load the dataset
try:
    data = pd.read_csv('Training.csv')

    # --- THIS IS THE FINAL FIX ---
    # Create the target variable (y)
    y = data['prognosis']
    
    # Create the feature set (X) by dropping the target column
    # This is a more robust way than slicing and avoids issues with extra columns
    X = data.drop('prognosis', axis=1)
    
    # Also, drop any extra unnamed columns that might exist in the CSV
    if 'Unnamed: 133' in X.columns:
        X = X.drop('Unnamed: 133', axis=1)
    # --- END OF FIX ---

    # 2. Convert text disease names into numbers
    print("Encoding text labels to numbers...")
    label_encoder = LabelEncoder()
    y_encoded = label_encoder.fit_transform(y)

    # 3. Train the model
    print("Training the model...")
    X_train, X_test, y_train_encoded, y_test_encoded = train_test_split(X, y_encoded, test_size=0.2, random_state=42)
    dt_model = DecisionTreeClassifier()
    dt_model.fit(X_train, y_train_encoded)

    # 4. Save the model and helper files
    print("Saving the model and helper files...")
    with open('disease_model.pkl', 'wb') as f:
        pickle.dump(dt_model, f)

    with open('model_columns.pkl', 'wb') as f:
        pickle.dump(X.columns.tolist(), f)

    with open('label_encoder.pkl', 'wb') as f:
        pickle.dump(label_encoder, f)

    print("\n---")
    print("✅ Model trained and saved successfully! The error is now fixed.")
    print("---")

except Exception as e:
    print(f"\n--- An error occurred ---")
    print(f"Error: {e}")
    print("Please ensure the 'Training.csv' file is in the correct folder and is not corrupted.")
    print("---")