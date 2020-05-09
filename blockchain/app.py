# flask is a python web framework. it allows us to send and receive user requests
# with a minimal number of lines of non-web3py code. flask is beyond the scope of
# this tutorial so the flask code won't be commented. that way we can focus on
# how we're working with our smart contract
from flask import Flask, request, render_template

# solc is needed to compile our Solidity code
from solc import compile_source

import json

# web3 is needed to interact with eth contracts
from web3 import Web3, HTTPProvider

# we'll use ConciseContract to interact with our specific instance of the contract
from web3.contract import ConciseContract

from solc import compile_standard

# initialize our flask app
app = Flask(__name__)

# declare the candidates we're allowing people to vote for.
# note that each name is in bytes because our contract variable
# candidateList is type bytes32[]
VOTING_CANDIDATES = [b'Rama', b'Nick', b'Jose']

# open a connection to the local ethereum node
http_provider = HTTPProvider('http://localhost:8545')
eth_provider = Web3(http_provider).eth

# we'll use one of our default accounts to deploy from. every write to the chain requires a
# payment of ethereum called "gas". if we were running an actual test ethereum node locally,
# then we'd have to go on the test net and get some free ethereum to play with. that is beyond
# the scope of this tutorial so we're using a mini local node that has unlimited ethereum and
# the only chain we're using is our own local one
default_account = eth_provider.accounts[0]
# every time we write to the chain it's considered a "transaction". every time a transaction
# is made we need to send with it at a minimum the info of the account that is paying for the gas
transaction_details = {
    'from': default_account,
}


# compile the contract
compiled_code = compile_standard(
{
    "language": "Solidity",
    "sources": {
        "voting.sol": {
            "content": '''
		pragma solidity ^0.6.7;
		// We have to specify what version of compiler this code will compile with

		contract Voting {
		  /* mapping field below is equivalent to an associative array or hash.
		  The key of the mapping is the candidate name stored as type bytes32 and value is
		  an unsigned integer to store the vote count
		  */
		  
		  mapping (bytes32 => uint8) public votesReceived;
		  
		  /* Solidity doesn't let you pass in an array of strings in the constructor (yet).
		  We will use an array of bytes32 instead to store the list of candidates
		  */
		  
		  bytes32[] public candidateList;

		  /* This is the constructor which will be called once when you
		  deploy the contract to the blockchain. When we deploy the contract,
		  we will pass an array of candidates who will be contesting in the election
		  */
		  constructor(bytes32[] memory candidateNames) public {
		    candidateList = candidateNames;
		  }

		  /* Accessing class attributes directly with web3py or web3js sometimes leads to 
		  unpredictable behavior. To be safe we create a getter method that returns our 
		  array of candidate names.
		  */
		  function getCandidateList() public view returns (bytes32[] memory) {
		    return candidateList;
		  }

		  // This function returns the total votes a candidate has received so far
		  function totalVotesFor(bytes32 candidate) public view returns (uint8) {
		    require(validCandidate(candidate) == true);
		    return votesReceived[candidate];
		  }

		  // This function increments the vote count for the specified candidate. This
		  // is equivalent to casting a vote
		  function voteForCandidate(bytes32 candidate) public {
		    require(validCandidate(candidate) == true);
		    votesReceived[candidate] += 1;
		  }

		  function validCandidate(bytes32 candidate) public view returns (bool) {
		    for(uint i = 0; i < candidateList.length; i++) {
		      if (candidateList[i] == candidate) {
			return true;
		      }
		    }
		    return false;
		  }
		}

'''
        }
    },
     "settings":
         {
             "outputSelection": {
                 "*": {
                     "*": [
                         "metadata", "evm.bytecode"
                         , "evm.bytecode.sourceMap"
                     ]
                 }
             }
         }
}
)

contract_name = 'Voting'

contract_bytecode = compiled_code['contracts']['voting.sol']['Voting']['evm']['bytecode']['object']
contract_abi = json.loads(compiled_code['contracts']['voting.sol']['Voting']['metadata'])['output']['abi']
contract_factory = eth_provider.contract(
    abi=contract_abi,
    bytecode=contract_bytecode,
)

contract_constructor = contract_factory.constructor(VOTING_CANDIDATES)

transaction_hash = contract_constructor.transact(transaction_details)

transaction_receipt = eth_provider.getTransactionReceipt(transaction_hash)
contract_address = transaction_receipt['contractAddress']

contract_instance = eth_provider.contract(
    abi=contract_abi,
    address=contract_address,
    ContractFactoryClass=ConciseContract,
)


@app.route('/', methods=['GET', 'POST'])
def index():
    alert = ''
    candidate_name = request.form.get('candidate')
    if request.method == 'POST' and candidate_name:
        candidate_name_bytes = candidate_name.encode()
        try:
            contract_instance.voteForCandidate(candidate_name_bytes, transact=transaction_details)
        except ValueError:
            alert = f'{candidate_name} is not a voting option!'

    candidate_names = contract_instance.getCandidateList()
    candidates = {}
    for candidate_name in candidate_names:
        votes_for_candidate = contract_instance.totalVotesFor(candidate_name)
        candidate_name_string = candidate_name.decode().rstrip('\x00')
        candidates[candidate_name_string] = votes_for_candidate

    return render_template('index.html', candidates=candidates, alert=alert)


if __name__ == '__main__':
    app.run(debug=True, use_reloader=False)
