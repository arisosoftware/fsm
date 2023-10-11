def extract_flight_info(input_text):
    # Split the input into lines
    lines = input_text.split('\n')

    # Initialize a variable to store flight information
    flight_info = []

    # Initialize a flag to indicate when to capture flight data
    capture_data = False

    # Process each line
    for line in lines:
        if "Total_Time::" in line:
            # Stop capturing data when "Total_Time::" is encountered
            capture_data = False
        if capture_data:
            flight_info.append(line)
        if "Total_Time::" in line:
            # Start capturing data when "Total_Time::" is encountered
            capture_data = True

    return flight_info

def extract_flight_columns(flight_info):
    airlines = []
    flight_numbers = []
    departure_times = []
    arrival_times = []
    layovers = []
    classes = []
    durations = []

    for line in flight_info:
        parts = line.split('\t')
        if len(parts) >= 9:
            airlines.append(parts[0])
            flight_numbers.append(parts[1])
            departure_times.append(parts[2])
            arrival_times.append(parts[5])
            layovers.append(parts[6])
            classes.append(parts[7])
            durations.append(parts[8])

    return airlines, flight_numbers, departure_times, arrival_times, layovers, classes, durations

# Example usage:
input_text = """
$3,120.50	+	$162.46	(taxes) =	$3,282.96	
Cathay Pacific	Flight 825	3:55pm	Sun, Oct 15	Toronto (YYZ)	7:15pm	Mon, Oct 16	Hong Kong (HKG)	22h 30m layover in Hong Kong	Economy	Nonstop - 15h 20m	
Cathay Pacific	Flight 978	5:45pm	Tue, Oct 17	Hong Kong (HKG)	7:15pm	Tue, Oct 17	Xiamen (XMN)	Economy	Nonstop - 1h 30m	
Total_Time:: 39h 20m	
Cathay Pacific	Flight 973	12:15pm	Sun, Oct 22	Xiamen (XMN)	1:50pm	Sun, Oct 22	Hong Kong (HKG)	3h 30m layover in Hong Kong	Economy	Nonstop - 1h 35m	
Cathay Pacific	Flight 826	5:20pm	Sun, Oct 22	Hong Kong (HKG)	8:35pm	Sun, Oct 22	Toronto (YYZ)	Economy	Nonstop - 15h 15m	
Total_Time:: 20h 20m	
"""

flight_info = extract_flight_info(input_text)
airlines, flight_numbers, departure_times, arrival_times, layovers, classes, durations = extract_flight_columns(flight_info)

# Print the extracted columns
print("Airlines:", airlines)
print("Flight Numbers:", flight_numbers)
print("Departure Times:", departure_times)
print("Arrival Times:", arrival_times)
print("Layovers:", layovers)
print("Classes:", classes)
print("Durations:", durations)
