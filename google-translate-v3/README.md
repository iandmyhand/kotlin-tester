# Google Translate V3 API Tester

## Project Setting

- Create a .env file to the project root directory.
  - Add below variables:
    
    ```
    PROJECT_ID=
    ```

- Create a google-service-key.json file to the project root directory.
  - Fill with the GCP's service account key(json).

- Add environment variable. 
  - Key: GOOGLE_APPLICATION_CREDENTIALS / Value: /path/to/kotlin-tester/google-translate-v3/google-service-key.json 
  - If you use IntelliJ, You can add variable to IntelliJ's Run/Debug Configurations > Environment variables.
